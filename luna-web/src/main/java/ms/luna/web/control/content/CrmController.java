package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.util.*;
import ms.luna.web.common.PulldownCtrl;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.MerchantRegistCtrl;
import ms.luna.web.control.common.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */
@Controller
@RequestMapping("/content/crm")
public class CrmController extends BasicController {

    private static final String CRM_URI = "/merchant_crm.jsp";
    public static final String menu = "crm";

    @Autowired
    private PulldownCtrl pulldownCtrl;
    @Autowired
    private ManageMerchantService manageMerchantService;

    private static List<Map<String, String>> lstMerchantStatus = new ArrayList<Map<String, String>>();

    /**
     * 商户管理页面初始化
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        MsUser msUser = (MsUser) session.getAttribute("msUser");
        String luna_nm = msUser.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownCtrl.loadCategorys());
        model.addObject("provinces", pulldownCtrl.loadProvinces());
        model.addObject("merchantStatuMaps", this.loadMerchantStatus());
        model.addObject("luna_nm", luna_nm);

        return model;
    }

    /**
     * 商户添加页面初始化-
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/initAddPage")
    public ModelAndView init_add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        session.setAttribute("menu_selected", "crm");
        MsUser msUser = (MsUser) session.getAttribute("msUser");
        String luna_nm = msUser.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm_add.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownCtrl.loadCategorys());
        model.addObject("provinces", pulldownCtrl.loadProvinces());
        model.addObject("merchantStatuMaps", this.loadMerchantStatus());
        model.addObject("luna_nm", luna_nm);

        return model;
    }

    /**
     * 商户添加页面初始化-
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/initEditPage")
    public ModelAndView init_edit(@RequestParam(required = true) String merchant_id,HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        session.setAttribute("menu_selected", "crm");
        MsUser msUser = (MsUser) session.getAttribute("msUser");
        String luna_nm = msUser.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm_edit.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownCtrl.loadCategorys());
        model.addObject("provinces", pulldownCtrl.loadProvinces());
        model.addObject("merchantStatuMaps", this.loadMerchantStatus());
        model.addObject("luna_nm", luna_nm);
        model.addObject("merchant_id", merchant_id);

        return model;
    }

    /**
     * 商户异步搜索
     *
     * @param like_filter_nm
     * @param offset
     * @param limit
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchMerchants(
            String like_filter_nm,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit,
            HttpServletRequest request, HttpServletResponse response)throws IOException {
        JSONObject resJSON = JSONObject.parseObject("{}");
        resJSON.put("total", 0);
        try {
            JSONObject param = JSONObject.parseObject("{}");
            if (like_filter_nm != null && !like_filter_nm.isEmpty()) {
                like_filter_nm = URLDecoder.decode(like_filter_nm, "UTF-8");
                like_filter_nm = like_filter_nm.trim();
                if (!like_filter_nm.isEmpty()) {
                    param.put("like_filter_nm", like_filter_nm);
                }
            }

            if (offset != null) {
                param.put("min", offset);
            }
            if (limit != null) {
                param.put("max", limit);
            }

            // manageMerchantService
            JSONObject result = manageMerchantService.loadMerchants(param.toString());
            MsLogger.debug("method:loadMerchants, result from service: "+result.toString());

            if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                JSONArray arrays = data.getJSONArray("merchants");
                Integer total = data.getInteger("total");

                resJSON.put("total", total);
                JSONArray rows = JSONArray.parseArray("[]");

                for (int i = 0; i < arrays.size(); i++) {
                    JSONObject merchant = arrays.getJSONObject(i);

                    JSONObject row = JSONObject.parseObject("{}");
                    row.put("merchant_id", merchant.getString("merchant_id")); // 商户id
                    row.put("merchant_nm", merchant.getString("merchant_nm")); // 商户名称
                    row.put("category_nm", merchant.getString("category_nm")); // 业务种类
                    row.put("contact_nm", merchant.getString("contact_nm")); // 联系人名字
                    row.put("contact_phonenum", merchant.getString("contact_phonenum")); // 联系人电话
                    row.put("salesman_nm", merchant.getString("salesman_nm")); // 业务员名字
                    row.put("province_id", merchant.getString("province_id"));
                    row.put("city_id", merchant.getString("city_id"));
                    row.put("del_flg", merchant.getString("del_flg"));
                    Byte status_id = Byte.parseByte(merchant.getString("status_id"));
                    String status = VbConstant.MERCHANT_STATUS.ConvertStauts(status_id);
                    row.put("status", status); // 状态
                    if (merchant.containsKey("county_id")) {
                        row.put("county_id", merchant.getString("county_id"));
                    } else {
                        row.put("county_id", "ALL");
                    }
                    rows.add(row);
                }
                resJSON.put("rows", rows);
            }
        } catch (Exception e) {
            MsLogger.error("Failed to search merchants: "+ e);
        }
        return resJSON;
    }

    /**
     * 商户注册
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/regist")
    @ResponseBody
    public JSONObject createMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String contact_nm = request.getParameter("contact_nm"); // 联系人名字
            String contact_phonenum = request.getParameter("contact_phonenum");//
            // 联系人手机
            String contact_mail = request.getParameter("contact_mail");// 联系人邮箱
            String merchant_nm = request.getParameter("merchant_nm");// 商户名字
            String merchant_phonenum = request.getParameter("merchant_phonenum");//
            // 商户电话
            String category_id = request.getParameter("merchant_cata");// 业务种类
            String province_id = request.getParameter("province");// 省份id
            String city_id = request.getParameter("city");// 城市id
            String merchant_addr = request.getParameter("merchant_addr");// 商户地址
            String merchant_info = request.getParameter("merchant_info");// 商户介绍
            String lat = request.getParameter("lat");// 经纬度
            String lng = request.getParameter("lng");
            String status_id = request.getParameter("status");
            String salesman_nm = request.getParameter("salesman");
            String resource_content = request.getParameter("resource_content");// 证件
            String county_id = request.getParameter("county");// 区县id

            String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum,
                    category_id, resource_content, province_id, city_id, county_id, merchant_addr, lat, lng,
                    merchant_info, status_id, salesman_nm);

            // 如果邮箱输入不为空，则检测邮箱
            if (!contact_mail.isEmpty()) {
                if (!CharactorUtil.checkEmail(contact_mail)) {
                    inputInfo = inputInfo + "邮件输入有误, email:"+contact_mail;
                }
            }

            // 输入校验通过
            // String inputInfo = "";
            if (inputInfo.equals("")) {
                JSONObject param = JSONObject.parseObject("{}");

                param.put("contact_nm", contact_nm);
                param.put("contact_phonenum", contact_phonenum);
                param.put("contact_mail", contact_mail);
                param.put("merchant_nm", merchant_nm);
                param.put("merchant_phonenum", merchant_phonenum);
                param.put("category_id", category_id);
                param.put("province_id", province_id);
                param.put("city_id", city_id);
                param.put("merchant_addr", merchant_addr);
                param.put("merchant_info", merchant_info);
                param.put("status_id", status_id);
                param.put("salesman_nm", salesman_nm);
                if(!lat.equals("")){
                    param.put("lat", lat);
                }
                if(!lng.equals("")){
                    param.put("lng", lng);
                }
                if (!county_id.equals("ALL")) {
                    param.put("county_id", county_id);
                }
                if (!resource_content.equals("")) {
                    param.put("resource_content", resource_content);
                }

                JSONObject result = manageMerchantService.createMerchant(param.toString());
                String code = result.getString("code");
                if ("0".equals(code)) {
                    return FastJsonUtil.sucess("编辑成功！merchant_nm:" + merchant_nm);
                } else if ("1".equals(code)) {
                    return FastJsonUtil.error("3", "用户重名(下手慢了),merchant_nm:" + merchant_nm);
                } else if ("2".equals(code)) {
                    return FastJsonUtil.error("4", "业务员不存在！salesman_nm:" + salesman_nm);
                } else {
                    return FastJsonUtil.error("-1", "编辑失败！");
                }
            } else {
                return FastJsonUtil.error("1", "校验错误！inputInfo:" + inputInfo);
            }
        } catch (Exception e) {
            MsLogger.error("创建商户失败." + e.getMessage());
            return FastJsonUtil.error("-1", "创建失败！");
        }
    }

    /**
     * 删除商户
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/merchantId/{merchant_id}")
    @ResponseBody
    public JSONObject deleteMerchantById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            // 根据id删除商户信息（直接删除而非del_flag = 1）
            String merchant_id = request.getParameter("merchant_id");
            if (merchant_id == null || merchant_id.isEmpty()) {
                return FastJsonUtil.error("-1", "参数错误");
            }

            // 获得业务员name和Id
            HttpSession session = request.getSession(false);
            MsUser msUser = (MsUser) session.getAttribute("msUser");
            String uniqueId = msUser.getUniqueId();

            JSONObject param = JSONObject.parseObject("{}");
            param.put("salesman_id", uniqueId);
            param.put("merchant_id", merchant_id);

            JSONObject result = manageMerchantService.deleteMerchantById(param.toString());
            MsLogger.debug("method:deleteMerchantById, result from service: " + result.toString());

            if ("0".equals(result.getString("code"))) {
                return result;
            } else if ("1".equals(result.getString("code"))) {
                return FastJsonUtil.error("1", "非本业务员");
            } else if ("2".equals(result.getString("code"))) {
                return FastJsonUtil.error("2", "商户id不存在");
            } else { // 异常
                return FastJsonUtil.error(result.getString("code"), result.getString("msg"));
            }
        } catch (Exception e) {
            MsLogger.error("Failed to del merchant. " +  e.getMessage());
            return FastJsonUtil.error("-1", "Failed to del merchant");
        }
    }

    /**
     * 关闭商户
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/close")
    @ResponseBody
    public JSONObject closeMerchantById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String merchant_id = request.getParameter("merchant_id");
            if (merchant_id == null || merchant_id.isEmpty()) {
                return FastJsonUtil.error("-1", "参数错误");
            }
            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchant_id);

            JSONObject result = manageMerchantService.closeMerchantById(param.toString());
            MsLogger.debug("method:closeMerchantById, result from service: "+result.toString());

            if ("0".equals(result.getString("code"))) {
                response.getWriter().print(result.toString());
                return result;
            } else if ("1".equals(result.getString("code"))) {
                return FastJsonUtil.error("1", "商户id不存在");
            } else { // 异常
                return FastJsonUtil.error(result.getString("code"), result.getString("msg"));
            }
        } catch (Exception e) {
            MsLogger.error("Failed to close merchant. " + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to close merchant");
        }
    }

    /**
     * 开启商户
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/open")
    @ResponseBody
    public JSONObject openMerchantById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String merchant_id = request.getParameter("merchant_id");
            if (merchant_id == null || merchant_id.isEmpty()) {
                return FastJsonUtil.error("-2", "参数错误");
            }
            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchant_id);

            JSONObject result = manageMerchantService.openMerchantById(param.toString());
            MsLogger.debug("method:openMerchantById, result from service: "+result.toString());

            if ("0".equals(result.getString("code"))) {
                return result;
            } else if ("1".equals(result.getString("code"))) {
                return FastJsonUtil.error("1", "商户id不存在");
            } else { // 异常
                return FastJsonUtil.error(result.getString("code"), result.getString("msg"));
            }
        } catch (Exception e) {
            return FastJsonUtil.error("-1", "Failed to open merchant:" + VbUtility.printStackTrace(e));
        }
    }

    /**
     * 加载单个商户信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/merchantId/{merchantId}")
    @ResponseBody
    public JSONObject loadMerchant(
            @PathVariable("merchantId") String merchantId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/html; charset=UTF-8");
            String merchant_id = request.getParameter("merchant_id");

            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchantId);

            JSONObject results = manageMerchantService.loadMerchantById(param.toString());
            MsLogger.debug("method:loadMerchantById, result from service: " + results.toString());

            if ("0".equals(results.getString("code"))) {
                JSONObject result = results.getJSONObject("data");
                JSONObject merchant = JSONObject.parseObject("{}");
                merchant.put("merchant_id", result.getString("merchant_id"));
                merchant.put("merchant_nm", result.getString("merchant_nm"));
                merchant.put("merchant_phonenum", result.getString("merchant_phonenum"));
                merchant.put("category_id", result.getString("category_id"));
                merchant.put("province_id", result.getString("province_id"));
                merchant.put("city_id", result.getString("city_id"));
                merchant.put("merchant_addr", result.getString("merchant_addr"));
                merchant.put("merchant_info", result.getString("merchant_info"));
                merchant.put("contact_nm", result.getString("contact_nm"));
                merchant.put("contact_phonenum", result.getString("contact_phonenum"));
                merchant.put("contact_mail", result.getString("contact_mail"));
                merchant.put("salesman_id", result.getString("salesman_id"));
                merchant.put("salesman_nm", result.getString("salesman_nm"));
                merchant.put("status_id", result.getString("status_id"));
                if(result.containsKey("lat")){
                    merchant.put("lat", result.getString("lat"));
                }else{
                    merchant.put("lat", "");
                }
                if(result.containsKey("lng")){
                    merchant.put("lng", result.getString("lng"));
                }else{
                    merchant.put("lng", "");
                }
                if (result.containsKey("county_id")) {
                    merchant.put("county_id", result.getString("county_id"));
                } else {
                    merchant.put("county_id", "ALL");
                }
                if (result.containsKey("resource_content")) {
                    merchant.put("resource_content", result.getString("resource_content"));
                } else {
                    merchant.put("resource_content", "");
                }
                return FastJsonUtil.sucess("加载成功！", merchant);
            } else {
                return FastJsonUtil.error("1", "加载失败！");
            }

        } catch (Exception e) {
            return FastJsonUtil.error("-1", "Failed to load merchant:" + VbUtility.printStackTrace(e));
        }
    }

    /**
     * 更新商户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/editMerchant")
    @ResponseBody
    public JSONObject editMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String merchant_id = request.getParameter("merchant_id_edit"); // 商户id
            String contact_nm = request.getParameter("contact_nm_edit"); // 联系人名字
            String contact_phonenum = request.getParameter("contact_phonenum_edit");// 联系人手机
            String contact_mail = request.getParameter("contact_mail_edit");// 联系人邮箱
            String merchant_nm = request.getParameter("merchant_nm_edit");// 商户名字
            String merchant_phonenum = request.getParameter("merchant_phonenum_edit");// 商户电话
            String category_id = request.getParameter("merchant_cata_edit");// 业务种类
            String province_id = request.getParameter("province_edit");// 省份id
            String city_id = request.getParameter("city_edit");// 城市id
            String merchant_addr = request.getParameter("merchant_addr_edit");// 商户地址
            String merchant_info = request.getParameter("merchant_info_edit");// 商户介绍
            String lat = request.getParameter("lat_edit");// 经纬度
            String lng = request.getParameter("lng_edit");
            String status_id = request.getParameter("status_edit");
            String salesman_nm = request.getParameter("salesman_edit");

            String county_id = request.getParameter("county_edit");// 区县id
            String resource_content = request.getParameter("resource_content_edit");

            String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum,
                    category_id, resource_content, province_id, city_id, county_id, merchant_addr, lat, lng,
                    merchant_info, status_id, salesman_nm);
            // 输入校验通过
            if (inputInfo.equals("")) {
                JSONObject param = JSONObject.parseObject("{}");

                param.put("contact_nm", contact_nm);
                param.put("contact_phonenum", contact_phonenum);
                param.put("contact_mail", contact_mail);
                param.put("merchant_id", merchant_id);
                param.put("merchant_nm", merchant_nm);
                param.put("merchant_phonenum", merchant_phonenum);
                param.put("category_id", category_id);
                param.put("province_id", province_id);
                param.put("city_id", city_id);
                param.put("merchant_addr", merchant_addr);
                param.put("merchant_info", merchant_info);
                param.put("status_id", status_id);
                param.put("salesman_nm", salesman_nm);
                if(!lat.equals("")){
                    param.put("lat", lat);
                }
                if(!lng.equals("")){
                    param.put("lng", lng);
                }
                if (!county_id.equals("ALL")) {
                    param.put("county_id", county_id);
                }
                if (!resource_content.equals("无")) {
                    param.put("resource_content", resource_content);
                }
                JSONObject result = manageMerchantService.updateMerchantById(param.toString());
                MsLogger.debug("method:updateMerchantById, result from service: "+result.toString());

                String code = result.getString("code");
                if ("0".equals(code)) {
                    return FastJsonUtil.sucess("编辑成功！");
                } else if ("1".equals(code)) {
                    return FastJsonUtil.error("3", "用户重名（下手慢了）,merchant_nm:"+merchant_nm);
                } else if ("2".equals(code)) {
                    return FastJsonUtil.error("4", "业务员不存在！salesman_nm:"+salesman_nm);
                } else {
                    return FastJsonUtil.error("-1", "编辑失败！");
                }
            } else {
                return FastJsonUtil.error("1", "校验错误！");
            }
        } catch (Exception e) {
            return FastJsonUtil.error("-1", "Failed to edit merchant, " + e);
        }
    }

    /**
     * 检查商户名称是否存在
     *
     * @param merchant_nm
     * @param merchant_id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
    @ResponseBody
    public JSONObject checkMerchantName(
            @RequestParam(required = true, value = "merchant_nm") String merchant_nm,
            @RequestParam(required = false, value = "merchant_id") String merchant_id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject param = new JSONObject();
            param.put("merchant_nm", merchant_nm);
            JSONObject result;
            if (merchant_nm == null) {
                result = manageMerchantService.isAddedMerchantNmEist(param.toString());// 添加商户
            } else {
                param.put("merchant_id", merchant_id);
                result = manageMerchantService.isEditedMerchantNmEist(param.toString());// 编辑商户
            }
            MsLogger.debug("method:isAddedMerchantNmEist, result from service: " + result.toString());

            if ("1".equals(result.getString("code"))) {
                result.put("code", "0");
            } else if ("0".equals(result.getString("code"))) {
                result.put("code", "1");
            }
            return result;

        } catch (Exception e) {
            MsLogger.error("Failed to check merchant name. " + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to check merchant name. ");
        }

    }

    /**
     * 异步上传图片（创建）
     *
     * @param request
     * @param response
     * @throws IOException
     */
    // 原Api接口:/manage_merchant.do?method=upload_thumbnail_add
    @RequestMapping(method = RequestMethod.POST, value = "/uploadThumbnail")
    @ResponseBody
    public JSONObject uploadThumbnailAdd(@RequestParam(required = true, value = "thumbnail_fileup_add") MultipartFile file,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String orignalFileName = file.getOriginalFilename();
            String ext = VbUtility.getExtensionOfPicFileName(orignalFileName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileName = "/" + sdf.format(date) + "/" + VbMD5.generateToken() + ext;
            byte[] bytes = file.getBytes();// 获得文件内容
            JSONObject result = COSUtil.getInstance().uploadLocalFile2Cloud(COSUtil.LUNA_BUCKET, bytes,
                    localServerTempPath, COSUtil.getLunaCRMRoot() + fileName);// 上传
            MsLogger.debug("method:uploadLocalFile2Cloud, result from service: " + result.toString());

            return result;
        } catch (Exception e) {
            MsLogger.error(e);
            return FastJsonUtil.error("-1", "Failed to upload thumbnail (add): " + VbUtility.printStackTrace(e));
        }
        // super.uploadLocalFile2Cloud(request, response, file,
        // COSUtil.picAddress);
    }

    /**
     * 加载商户状态列表（to be 改进）
     *
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> loadMerchantStatus() throws Exception {
        if (!lstMerchantStatus.isEmpty()) {
            return lstMerchantStatus;
        }
        synchronized (PulldownCtrl.class) {
            if (lstMerchantStatus.isEmpty()) {
                Map<String, String> status5 = new LinkedHashMap<String, String>();
                status5.put("status_id", VbConstant.MERCHANT_STATUS.CODE.待处理 + "");
                status5.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_待处理);
                lstMerchantStatus.add(status5);

                Map<String, String> status4 = new LinkedHashMap<String, String>();
                status4.put("status_id", VbConstant.MERCHANT_STATUS.CODE.信息核实 + "");
                status4.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_信息核实);
                lstMerchantStatus.add(status4);

                Map<String, String> status2 = new LinkedHashMap<String, String>();
                status2.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品制作中 + "");
                status2.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品制作中);
                lstMerchantStatus.add(status2);

                Map<String, String> status1 = new LinkedHashMap<String, String>();
                status1.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品交付 + "");
                status1.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品交付);
                lstMerchantStatus.add(status1);

                Map<String, String> status3 = new LinkedHashMap<String, String>();
                status3.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品追踪 + "");
                status3.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品追踪);
                lstMerchantStatus.add(status3);

                Map<String, String> status6 = new LinkedHashMap<String, String>();
                status6.put("status_id", VbConstant.MERCHANT_STATUS.CODE.无效商户 + "");
                status6.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_无效商户);
                lstMerchantStatus.add(status6);
            }
        }
        return lstMerchantStatus;
    }

    /**
     * 输入检查
     *
     * @return
     */
    private String checkInput(String contact_nm, String contact_phonenum, String contact_mail, String merchant_nm,
                              String merchant_phonenum, String category_id, String resource_content, String province_id, String city_id,
                              String county_id, String merchant_addr, String lat, String lng, String merchant_info, String status_id,
                              String salesman_nm) {

        String inputInfo = checkContactNm(contact_nm) + checkMerchantNm(merchant_nm) + checkCategoryId(category_id)
                + checkResourceContent(resource_content) + checkProvinceId(province_id) + checkCityId(city_id)
                + checkCountyId(county_id) + checkMerchantAddr(merchant_addr) + checkStatusId(status_id)
                + checkSalesmanNm(salesman_nm);

        // 如果有经纬度输入，则检测经纬度
        if (!lat.isEmpty() && !lng.isEmpty()) {
            inputInfo = inputInfo + checkLatAndLng(lat, lng);
        } else if (lat.isEmpty() && lng.isEmpty()) {
            // 允许经纬度为空
        } else {
            inputInfo = inputInfo + "经纬度输入有误";
        }

        // 如果邮箱输入不为空，则检测邮箱
        if(contact_mail == null){
            return "邮件输入有误！";
        }
        if (!contact_mail.isEmpty()) {
            if (!CharactorUtil.checkEmail(contact_mail)) {
                inputInfo = inputInfo + "邮件输入有误！";
            }
        }

        if (!CharactorUtil.checkPhoneNum(merchant_phonenum)) {
            inputInfo = inputInfo + "商户电话输入有误！";
        }
        if (!CharactorUtil.checkPhoneNum(contact_phonenum)) {
            inputInfo = inputInfo + "联系人电话输入有误！";
        }
        return inputInfo;
    }

    /**
     * 业务员名称检测
     *
     * @param salesman_nm
     * @return
     */
    private String checkSalesmanNm(String salesman_nm) {
        if (salesman_nm == null || salesman_nm.length() == 0)
            return "业务员名称有误！";
        return "";
    }

    /**
     * 处理状态检测
     *
     * @param status_id
     * @return
     */
    private String checkStatusId(String status_id) {
        if (status_id == null || status_id.length() == 0)
            return "处理状态有误！";
        return "";
    }

    /**
     * 经纬度检测
     *
     * @param lng
     * @return
     */
    private String checkLatAndLng(String lat, String lng) {
        if (lat == null) {
            return "纬度有误";
        }
        if (lng == null) {
            return "经度有误";
        }
        if (lat.isEmpty() && lng.isEmpty()) {
            return "";
        }

        // 纬度检测
        boolean hasError = false;
        String regPattern = "([+-]?\\d{1,3})(\\.\\d{0,6})";
        if (Pattern.matches(regPattern, lat)) {
            float latFloat = Float.parseFloat(lat);
            if (latFloat > 90 || latFloat < -90) {
                hasError = true;
            }
        } else {
            hasError = true;
        }
        if (hasError == true) {
            return "纬度有误";
        }

        // 经度检测
        if (Pattern.matches(regPattern,lng)) {
            float lngFloat = Float.parseFloat(lng);
            if (lngFloat > 180 || lngFloat < -180) {
                hasError = true;
            }
        }else{
            hasError = true;
        }
        if (hasError == true) {
            return "经度有误";
        }

        return "";
    }

    /**
     * 纬度检测
     *
     * @param lat
     * @return
     */
    private String checkLat(String lat) {
        if (lat == null || lat.length() == 0)
            return "纬度有误！";
        return "";
    }

    /**
     * 图片地址检测
     *
     * @param resource_content
     * @return
     */
    private String checkResourceContent(String resource_content) {
        if (resource_content == null)
            return "图片地址有误！";
        return "";
    }

    /**
     * 文件检测
     *
     * @param file
     * @return
     */
    private String checkFile(MultipartFile file, String license) {
        // 存在上传图片
        if (license.equals("是")) {
            if (file == null || file.isEmpty())
                return "文件为空！";
            // 文件太大超过预定的1M
            if (file.getSize() > COSUtil.图片上传大小分界线1M)
                return "文件大小过大！";

            // 是否是**.**形式文件
            String fileName = file.getOriginalFilename();
            int point = fileName.lastIndexOf(".");
            if (point == -1 || point == fileName.length() - 1)
                return "文件出错！";

            String extName = fileName.substring(point + 1);
            extName = extName.toLowerCase();
            // 非jpg或png文件
            if (extName.length() == 0 || (!"jpg".equals(extName)) && (!"png".equals(extName)))
                return "非jpg或png文件！";
        }
        return "";
    }

    /**
     * 区/县id检测
     *
     * @param county_id
     * @return
     */
    private String checkCountyId(String county_id) {
        // if (county_id == null || county_id.length() == 0 ||
        // county_id.equals("ALL"))
        if (county_id == null || county_id.length() == 0)// 允许区/县不选择
            return "区/县输入有误";
        return "";
    }

    /**
     * 城市id检测
     *
     * @param city_id
     * @return
     */
    private String checkCityId(String city_id) {
        if (city_id == null || city_id.length() == 0 || city_id.equals("ALL"))
            return "城市输入有误";
        return "";
    }

    /**
     * 省份id检测
     *
     * @param province_id
     * @return
     */
    private String checkProvinceId(String province_id) {
        if (province_id == null || province_id.length() == 0 || province_id.equals("ALL"))
            return "省份输入有误！";
        return "";
    }

    /**
     * 业务种类检测
     *
     * @param category_id
     * @return
     */
    private String checkCategoryId(String category_id) {
        if (category_id == null || category_id.length() == 0)
            return "业务种类有误！";
        return "";
    }

    /**
     * license检测
     *
     * @param license
     * @return
     */
    private String checkLicense(String license) {
        if (license.equals("是") || license.equals("否"))
            // if (license.equals("是"))
            return "";
        return "License有误！";
    }

    /**
     * 联系人名字检测
     *
     * @param contact_nm
     * @return
     */
    private String checkContactNm(String contact_nm) {
        if (contact_nm == null || contact_nm.length() == 0)
            return "联系人名字有误！";
        return "";
    }

    /**
     * 商户名字检测
     *
     * @param merchant_nm
     * @return
     */
    private String checkMerchantNm(String merchant_nm) {
        if (merchant_nm == null || merchant_nm.length() == 0)
            return "商户名字有误!";
        return "";
    }

    /**
     * 商户地址检测
     *
     * @param merchant_addr
     * @return
     */
    private String checkMerchantAddr(String merchant_addr) {
        if (merchant_addr == null)
            return "商户地址有误！";
        return "";
    }

}
