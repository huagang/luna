package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.sc.LunaUserService;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.table.LunaRoleCategoryTable;
import ms.luna.biz.table.LunaRoleTable;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.CommonURI;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.control.common.PulldownController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private PulldownController pulldownController;
    @Autowired
    private ManageMerchantService manageMerchantService;
    @Autowired
    private LunaUserService lunaUserService;

    private static List<Map<String, String>> lstMerchantStatus = new ArrayList<Map<String, String>>();

    /**
     * 商户管理页面初始化
     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        String luna_nm = user.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownController.loadCategorys());
        model.addObject("provinces", pulldownController.loadProvinces());
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
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        session.setAttribute("menu_selected", "crm");
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        String luna_nm = user.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm_add.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownController.loadCategorys());
        model.addObject("provinces", pulldownController.loadProvinces());
        model.addObject("merchantStatuMaps", this.loadMerchantStatus());
        model.addObject("luna_nm", luna_nm);

        return model;
    }

    /**
     * 商户添加页面初始化-
     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "/initEditPage")
    public ModelAndView init_edit(
            @RequestParam(required = true, value = "merchantId") String merchant_id,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            MsLogger.error("session is null");
            return new ModelAndView("/error.jsp");
        }
        session.setAttribute("menu_selected", "crm");
        LunaUserSession user = SessionHelper.getUser(request.getSession(false));
        String luna_nm = user.getNickName();

        ModelAndView model = new ModelAndView("/manage_crm_edit.jsp");
        model.addObject("basePath", request.getContextPath());
        model.addObject("categoryMap", pulldownController.loadCategorys());
        model.addObject("provinces", pulldownController.loadProvinces());
        model.addObject("merchantStatuMaps", this.loadMerchantStatus());
        model.addObject("luna_nm", luna_nm);
        model.addObject("merchant_id", merchant_id);

        return model;
    }

    /**
     * 商户异步搜索
     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject asyncSearchMerchants(
            @RequestParam(required = false) String like_filter_nm,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "30") Integer limit) throws IOException {
        JSONObject resJSON = JSONObject.parseObject("{}");
        resJSON.put("total", 0);
        try {
            JSONObject param = JSONObject.parseObject("{}");
            like_filter_nm = URLDecoder.decode(like_filter_nm, "UTF-8");
            like_filter_nm = like_filter_nm.trim();
            if (!like_filter_nm.isEmpty()) {
                param.put("like_filter_nm", like_filter_nm);
            }

            param.put("offset", offset);
            param.put("limit", limit);
            JSONObject result = manageMerchantService.loadMerchants(param.toString());
            MsLogger.debug("method:loadMerchants, result from service: " + result.toString());
            return result.getJSONObject("data");
        } catch (Exception e) {
            MsLogger.error("Failed to search merchants: " + e);
        }
        return resJSON;
    }

    /**
     * 商户注册
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createMerchant(HttpServletRequest request) throws IOException {
        try {
            String contact_nm = request.getParameter("contact_nm"); // 联系人名字
            String contact_phonenum = request.getParameter("contact_phonenum");// 联系人手机
            String contact_mail = request.getParameter("contact_mail");// 联系人邮箱
            String merchant_nm = request.getParameter("merchant_nm");// 商户名字
            String merchant_phonenum = request.getParameter("merchant_phonenum");// 商户电话
            String category_id = request.getParameter("merchant_cata");// 业务种类
            String province_id = request.getParameter("province");// 省份id
            String city_id = request.getParameter("city");// 城市id
            String merchant_addr = request.getParameter("merchant_addr");// 商户地址
            String status_id = request.getParameter("status");
            String salesman_nm = request.getParameter("salesman");
            String county_id = request.getParameter("county");// 区县id
            String businessName = RequestHelper.getString(request, "business_name");
            String businessCode = RequestHelper.getString(request, "business_code");

            String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum,
                    category_id, province_id, city_id, county_id, merchant_addr, status_id, salesman_nm, businessName, businessCode);

            if(!"".equals(inputInfo)) {
                return FastJsonUtil.error("1", "校验错误！inputInfo:" + inputInfo);
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            // 输入校验通过
            JSONObject param = new JSONObject();
            param.put("contact_nm", contact_nm);
            param.put("contact_phonenum", contact_phonenum);
            param.put("contact_mail", contact_mail);
            param.put("merchant_nm", merchant_nm);
            param.put("merchant_phonenum", merchant_phonenum);
            param.put("category_id", category_id);
            param.put("province_id", province_id);
            param.put("city_id", city_id);
            param.put("merchant_addr", merchant_addr);
            param.put("status_id", status_id);
            param.put("salesman_nm", salesman_nm);
            param.put("create_user", user.getNickName());
            param.put("updated_by_unique_id", user.getUniqueId());
            param.put("business_name", businessName);
            param.put("business_code", businessCode);
            if (!"ALL".equals(county_id)) {
                param.put("county_id", county_id);
            }

            JSONObject result = manageMerchantService.createMerchant(param.toString());
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("创建商户失败." + e.getMessage());
            return FastJsonUtil.error("-1", "创建商户失败！");
        }
    }

    /**
     * 删除商户
     *
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "")
    @ResponseBody
    public JSONObject deleteMerchantById(HttpServletRequest request) throws IOException {
        try {

            // 根据id删除商户信息（直接删除而非del_flag = 1）
            String merchant_id = request.getParameter("merchant_id");
            if (merchant_id == null || merchant_id.isEmpty()) {
                return FastJsonUtil.error("-1", "参数错误");
            }

            // 获得业务员name和Id
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            String uniqueId = user.getUniqueId();

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
            MsLogger.error("Failed to del merchant. " + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to del merchant");
        }
    }

    /**
     * 关闭商户
     *
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/merchantId/{merchant_id}/disable")
    @ResponseBody
    public JSONObject closeMerchantById(@PathVariable("merchant_id") String merchant_id) throws IOException {
        try {

            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchant_id);

            JSONObject result = manageMerchantService.closeMerchantById(param.toString());
            MsLogger.debug("method:closeMerchantById, result from service: " + result.toString());

            if ("0".equals(result.getString("code"))) {
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
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{merchant_id}/enable")
    @ResponseBody
    public JSONObject openMerchantById(@PathVariable("merchant_id") String merchant_id )throws IOException {
        try {

            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchant_id);

            JSONObject result = manageMerchantService.openMerchantById(param.toString());
            MsLogger.debug("method:openMerchantById, result from service: " + result.toString());

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
     */
    @RequestMapping(method = RequestMethod.GET, value = "/merchantId/{merchantId}")
    @ResponseBody
    public JSONObject loadMerchant(@PathVariable("merchantId") String merchantId) throws IOException {
        try {

            JSONObject param = JSONObject.parseObject("{}");
            param.put("merchant_id", merchantId);

            JSONObject result = manageMerchantService.loadMerchantById(param.toString());
            MsLogger.debug("method:loadMerchantById, result from service: " + result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to load merchant:" + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to load merchant");
        }
    }

//    /**
//     * 更新商户信息
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(method = RequestMethod.POST, value = "/edit")
//    @ResponseBody
//    public JSONObject editMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//
//            String merchant_id = request.getParameter("merchant_id_edit"); // 商户id
//            String contact_nm = request.getParameter("contact_nm_edit"); // 联系人名字
//            String contact_phonenum = request.getParameter("contact_phonenum_edit");// 联系人手机
//            String contact_mail = request.getParameter("contact_mail_edit");// 联系人邮箱
//            String merchant_nm = request.getParameter("merchant_nm_edit");// 商户名字
//            String merchant_phonenum = request.getParameter("merchant_phonenum_edit");// 商户电话
//            String category_id = request.getParameter("merchant_cata_edit");// 业务种类
//            String province_id = request.getParameter("province_edit");// 省份id
//            String city_id = request.getParameter("city_edit");// 城市id
//            String merchant_addr = request.getParameter("merchant_addr_edit");// 商户地址
//            String merchant_info = request.getParameter("merchant_info_edit");// 商户介绍
//            String lat = request.getParameter("lat_edit");// 经纬度
//            String lng = request.getParameter("lng_edit");
//            String status_id = request.getParameter("status_edit");
//            String salesman_nm = request.getParameter("salesman_edit");
//
//            String county_id = request.getParameter("county_edit");// 区县id
//            String resource_content = request.getParameter("resource_content_edit");
//
//            String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum,
//                    category_id, resource_content, province_id, city_id, county_id, merchant_addr, lat, lng,
//                    merchant_info, status_id, salesman_nm);
//            // 输入校验通过
//            if (inputInfo.equals("")) {
//                JSONObject param = JSONObject.parseObject("{}");
//
//                param.put("contact_nm", contact_nm);
//                param.put("contact_phonenum", contact_phonenum);
//                param.put("contact_mail", contact_mail);
//                param.put("merchant_id", merchant_id);
//                param.put("merchant_nm", merchant_nm);
//                param.put("merchant_phonenum", merchant_phonenum);
//                param.put("category_id", category_id);
//                param.put("province_id", province_id);
//                param.put("city_id", city_id);
//                param.put("merchant_addr", merchant_addr);
//                param.put("merchant_info", merchant_info);
//                param.put("status_id", status_id);
//                param.put("salesman_nm", salesman_nm);
//                if (!lat.equals("")) {
//                    param.put("lat", lat);
//                }
//                if (!lng.equals("")) {
//                    param.put("lng", lng);
//                }
//                if (!county_id.equals("ALL")) {
//                    param.put("county_id", county_id);
//                }
//                if (!resource_content.equals("无")) {
//                    param.put("resource_content", resource_content);
//                }
//                JSONObject result = manageMerchantService.updateMerchantById(param.toString());
//                MsLogger.debug("method:updateMerchantById, result from service: " + result.toString());
//
//                String code = result.getString("code");
//                if ("0".equals(code)) {
//                    return FastJsonUtil.sucess("编辑成功！");
//                } else if ("1".equals(code)) {
//                    return FastJsonUtil.error("3", "用户重名（下手慢了）,merchant_nm:" + merchant_nm);
//                } else if ("2".equals(code)) {
//                    return FastJsonUtil.error("4", "业务员不存在！salesman_nm:" + salesman_nm);
//                } else {
//                    return FastJsonUtil.error("-1", "编辑失败！");
//                }
//            } else {
//                return FastJsonUtil.error("1", "校验错误！");
//            }
//        } catch (Exception e) {
//            return FastJsonUtil.error("-1", "Failed to edit merchant, " + e);
//        }
//    }

    /**
     * 更新商户信息
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ResponseBody
    public JSONObject editMerchant(HttpServletRequest request) throws IOException {
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
            String status_id = request.getParameter("status_edit");
            String salesman_nm = request.getParameter("salesman_edit");
            String county_id = request.getParameter("county_edit");// 区县id

            String businessName = RequestHelper.getString(request, "business_name");
            String businessCode = RequestHelper.getString(request, "business_code");

            if(StringUtils.isBlank(merchant_id)) {
                return FastJsonUtil.error("1", "商户id不合法");
            }
            
            String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum,
                    category_id, province_id, city_id, county_id, merchant_addr, status_id, salesman_nm, businessName, businessCode);
            if(!"".equals(inputInfo)) {
                return FastJsonUtil.error("1", "校验错误！");
            }

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
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
            param.put("status_id", status_id);
            param.put("salesman_nm", salesman_nm);
            param.put("county_id", !"ALL".equals(county_id)? county_id : null);

            param.put("business_name", businessName);
            param.put("business_code", businessCode);
            param.put("updated_by_unique_id", user.getUniqueId());
            param.put("create_user", user.getNickName());

            JSONObject result = manageMerchantService.updateMerchantById(param.toString());
            MsLogger.debug("method:updateMerchantById, result from service: " + result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to edit merchant", e);
            return FastJsonUtil.error("-1", "Failed to edit merchant");
        }
    }

    /**
     * 检查商户名称是否存在
     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkName")
    @ResponseBody
    public JSONObject checkMerchantName(
            @RequestParam(required = true, value = "merchant_nm") String merchant_nm,
            @RequestParam(required = false, value = "merchant_id") String merchant_id) throws IOException {
        try {
            JSONObject param = new JSONObject();
            param.put("merchant_nm", merchant_nm);
            JSONObject result;
            if (merchant_id == null) {
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

//    /**
//     * 异步上传图片（创建）
//     */
//    // 原Api接口:/manage_merchant.do?method=upload_thumbnail_add
//    @RequestMapping(method = RequestMethod.POST, value = "/thumbnail/upload")
//    @ResponseBody
//    public String uploadThumbnail(@RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file) throws IOException {
//        try {
//
//            String orignalFileName = file.getOriginalFilename();
//            String ext = VbUtility.getExtensionOfPicFileName(orignalFileName);
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String fileName = "/" + sdf.format(date) + "/" + VbMD5.generateToken() + ext;
//            byte[] bytes = file.getBytes();// 获得文件内容
//            JSONObject result = COSUtil.getInstance().uploadLocalFile2Cloud(COSUtil.LUNA_BUCKET, bytes,
//                    localServerTempPath, COSUtil.getLunaCRMRoot() + fileName);// 上传
//            MsLogger.debug("method:uploadLocalFile2Cloud, result from service: " + result.toString());
//
//            return result.toString();
//        } catch (Exception e) {
//            MsLogger.error(e);
//            return FastJsonUtil.error("-1", "Failed to upload thumbnail (add): " + VbUtility.printStackTrace(e)).toString();
//        }
//    }

    // 邀请用户
    @RequestMapping(method = RequestMethod.POST, value = "/invite")
    @ResponseBody
    public JSONObject inviteUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String merchant_id = request.getParameter("merchant_id"); // 商户id
            if(StringUtils.isBlank(merchant_id)) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "merchant id invalid");
            }

            JSONObject param = new JSONObject();
            param.put("merchant_id", merchant_id);
            JSONObject merchantInfo = manageMerchantService.loadMerchantById(param.toString());
            // 获取商户email 和 business id
            if(!"0".equals(merchantInfo.getString("code"))) {
                return merchantInfo;
            }
            JSONObject data = merchantInfo.getJSONObject("data");
            String emails = data.getString("contact_mail");
            Integer business_id = data.getInteger("business_id");
            if(StringUtils.isBlank(emails)) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "contact mail not exist");
            }
            if(business_id == null) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "business id not exist");
            }
            int role_id = DbConfig.MERCHANT_ADMIN_ROLE_ID;// 角色id
            int category_id = DbConfig.MERCHANT_CATAGORY_ID; // 商家服务
            String extra = "{\"type\":\"business\",\"value\":["+ business_id +"]}";

            JSONObject param2 = new JSONObject();
            param2.put("emails", emails);
            param2.put("role_id", role_id);
            param2.put("webAddr", CommonURI.getAbsoluteUrlForServletPath(request, CommonURI.REGITSTER_SERVLET_PATH));
            param2.put(LunaRoleTable.FIELD_CATEGORY_ID, category_id);
            param2.put(LunaRoleCategoryTable.FIELD_EXTRA, extra);
            MsLogger.info("invite email: " + emails);

            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            JSONObject result = lunaUserService.inviteUser(user.getUniqueId(), param2);
            MsLogger.debug(result.toString());
            return result;
        } catch (Exception e) {
            MsLogger.error("Failed to invite user.", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "Failed to invite user.");
        }
    }

    /**
     * 加载商户状态列表（to be 改进）
     *
     */
    public List<Map<String, String>> loadMerchantStatus() throws Exception {
        if (!lstMerchantStatus.isEmpty()) {
            return lstMerchantStatus;
        }
        synchronized (PulldownController.class) {
            if (lstMerchantStatus.isEmpty()) {
                Map<String, String> status5 = new LinkedHashMap<>();
                status5.put("status_id", VbConstant.MERCHANT_STATUS.CODE.待处理 + "");
                status5.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_待处理);
                lstMerchantStatus.add(status5);

                Map<String, String> status4 = new LinkedHashMap<>();
                status4.put("status_id", VbConstant.MERCHANT_STATUS.CODE.信息核实 + "");
                status4.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_信息核实);
                lstMerchantStatus.add(status4);

                Map<String, String> status2 = new LinkedHashMap<>();
                status2.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品制作中 + "");
                status2.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品制作中);
                lstMerchantStatus.add(status2);

                Map<String, String> status1 = new LinkedHashMap<>();
                status1.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品交付 + "");
                status1.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品交付);
                lstMerchantStatus.add(status1);

                Map<String, String> status3 = new LinkedHashMap<>();
                status3.put("status_id", VbConstant.MERCHANT_STATUS.CODE.产品追踪 + "");
                status3.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_产品追踪);
                lstMerchantStatus.add(status3);

                Map<String, String> status6 = new LinkedHashMap<>();
                status6.put("status_id", VbConstant.MERCHANT_STATUS.CODE.无效商户 + "");
                status6.put("status_nm", VbConstant.MERCHANT_STATUS.NAME.名称_无效商户);
                lstMerchantStatus.add(status6);
            }
        }
        return lstMerchantStatus;
    }

    /**
     * 输入检查
     */
    private String checkInput(String contact_nm, String contact_phonenum, String contact_mail, String merchant_nm,
                              String merchant_phonenum, String category_id, String province_id, String city_id,
                              String county_id, String merchant_addr, String status_id, String salesman_nm,
                              String businessName, String businessCode) {

        String inputInfo = checkContactNm(contact_nm) + checkMerchantNm(merchant_nm) + checkCategoryId(category_id)
                + checkProvinceId(province_id) + checkCityId(city_id) + checkMerchantAddr(merchant_addr)
                + checkStatusId(status_id) + checkSalesmanNm(salesman_nm); // + checkCountyId(county_id)

        // 如果邮箱输入不为空，则检测邮箱
        if (contact_mail == null) {
            return "邮件输入不合法！";
        }
        if (!contact_mail.isEmpty()) {
            if (!CharactorUtil.checkEmail(contact_mail)) {
                inputInfo = inputInfo + "邮件输入不合法！";
            }
        }

        if (!CharactorUtil.checkPhoneNum(merchant_phonenum)) {
            inputInfo = inputInfo + "商户电话输入不合法！";
        }
        if (!CharactorUtil.checkPhoneNum(contact_phonenum)) {
            inputInfo = inputInfo + "联系人电话输入不合法！";
        }
        return inputInfo;
    }

    /**
     * 业务员名称检测
     *
     */
    private String checkSalesmanNm(String salesman_nm) {
        if (salesman_nm == null || salesman_nm.length() == 0)
            return "业务员名称不合法！";
        return "";
    }

    /**
     * 处理状态检测
     *
     */
    private String checkStatusId(String status_id) {
        if (status_id == null || status_id.length() == 0)
            return "处理状态不合法！";
        return "";
    }

    /**
     * 经纬度检测
     *
     */
    private String checkLatAndLng(String lat, String lng) {
        if (lat == null) {
            return "纬度不合法";
        }
        if (lng == null) {
            return "经度不合法";
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
            return "纬度不合法";
        }

        // 经度检测
        if (Pattern.matches(regPattern, lng)) {
            float lngFloat = Float.parseFloat(lng);
            if (lngFloat > 180 || lngFloat < -180) {
                hasError = true;
            }
        } else {
            hasError = true;
        }
        if (hasError == true) {
            return "经度不合法";
        }

        return "";
    }

    /**
     * 纬度检测
     *
     */
    private String checkLat(String lat) {
        if (lat == null || lat.length() == 0)
            return "纬度不合法！";
        return "";
    }

    /**
     * 图片地址检测
     *
     */
    private String checkResourceContent(String resource_content) {
        if (resource_content == null)
            return "图片地址不合法！";
        return "";
    }

    /**
     * 区/县id检测
     *
     */
    private String checkCountyId(String county_id) {
        // if (county_id == null || county_id.length() == 0 ||
        // county_id.equals("ALL"))
        if (county_id == null || county_id.length() == 0)// 允许区/县不选择
            return "区/县输入不合法";
        return "";
    }

    /**
     * 城市id检测
     *
     */
    private String checkCityId(String city_id) {
        if (city_id == null || city_id.length() == 0 || city_id.equals("ALL"))
            return "城市输入不合法";
        return "";
    }

    /**
     * 省份id检测
     *
     */
    private String checkProvinceId(String province_id) {
        if (province_id == null || province_id.length() == 0 || province_id.equals("ALL"))
            return "省份输入不合法！";
        return "";
    }

    /**
     * 业务种类检测
     *
     */
    private String checkCategoryId(String category_id) {
        if (category_id == null || category_id.length() == 0)
            return "业务种类不合法！";
        return "";
    }

    /**
     * license检测
     *
     */
    private String checkLicense(String license) {
        if (license.equals("是") || license.equals("否"))
            // if (license.equals("是"))
            return "";
        return "License不合法！";
    }

    /**
     * 联系人名字检测
     *
     */
    private String checkContactNm(String contact_nm) {
        if (contact_nm == null || contact_nm.length() == 0)
            return "联系人名字不合法！";
        return "";
    }

    /**
     * 商户名字检测
     *
     */
    private String checkMerchantNm(String merchant_nm) {
        if (merchant_nm == null || merchant_nm.length() == 0)
            return "商户名字不合法!";
        return "";
    }

    /**
     * 商户地址检测
     *
     */
    private String checkMerchantAddr(String merchant_addr) {
        if (merchant_addr == null)
            return "商户地址不合法！";
        return "";
    }

    private String checkBusinessName(String business_name) {
        if(StringUtils.isBlank(business_name) || business_name.length() > 32) {
            return "业务名称不合法";
        }
        return "";
    }

    private String checkBusinessCode(String business_code) {
        if(StringUtils.isBlank(business_code) || business_code.length() > 16) {
            return "业务简称不合法";
        }
        return "";
    }

}
