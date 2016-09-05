package ms.luna.web.control.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.*;
import ms.luna.common.LunaUserSession;
import ms.luna.common.PoiCommon;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.control.common.PulldownController;
import ms.luna.web.control.inner.UploadController;
import ms.luna.web.model.common.SimpleModel;
import ms.luna.web.model.managepoi.PoiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by greek on 16/8/5.
 */
@Controller("poiAddController")
@RequestMapping("/data/poi")
public class PoiAddController extends BasicController {
    @Autowired
    private ManagePoiService managePoiService;

    @Autowired
    private PulldownController pulldownController;

    @Autowired
    private VodPlayService vodPlayService;

    @Autowired
    private PoiController poiController;

    @Autowired
    private UploadController uploadController;

    public static final String menu = "poi";

    /**
     * 非共有字段（由程序控制）；共有字段，由维护人员修改代码。
     */

    /**
     * 页面初始化
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/addPage")
    public ModelAndView init(
            HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
        SessionHelper.setSelectedMenu(session, menu);
        PoiModel poiModel = new PoiModel();

        JSONObject params = new JSONObject();
        JSONObject result = managePoiService.initAddPoi(params.toString());
        MsLogger.debug(result.toString());

        if ("0".equals(result.getString("code"))) {
            JSONObject data = result.getJSONObject("data");
            JSONArray private_fields_def = data.getJSONArray("private_fields_def");
            poiController.initTags(mav, data.getJSONObject("common_fields_def"), null);
//            session.setAttribute("private_fields", private_fields_def);
            mav.addObject("private_fields", private_fields_def);
        } else {
            mav.setViewName("/error.jsp");
            return mav;
        }

        // 区域信息的下拉列表
        // 国家信息
        SimpleModel simpleModel = null;

        // 省份信息
        List<SimpleModel> lstProvinces = new ArrayList<SimpleModel>();
        try {
            for (Map<String, String> map : pulldownController.loadProvinces()) {
                simpleModel = new SimpleModel();
                simpleModel.setValue(map.get("province_id"));
                simpleModel.setLabel(map.get("province_nm_zh"));
                lstProvinces.add(simpleModel);
            }
        } catch (Exception e) {
            MsLogger.error(e);
            mav.setViewName("/error.jsp");
            return mav;
        }
        mav.addObject("provinces", lstProvinces);

        // 城市信息
        List<SimpleModel> lstCitys = new ArrayList<SimpleModel>();
        simpleModel = new SimpleModel();
        simpleModel.setValue(VbConstant.ZonePulldown.ALL);
        simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
        lstCitys.add(simpleModel);
        try {
            // 北京城市信息（需要初始化）
            for (Map<String, String> map : pulldownController.loadCitys("110000")) {
                simpleModel = new SimpleModel();
                simpleModel.setValue(map.get("city_id"));
                simpleModel.setLabel(map.get("city_nm_zh"));
                lstCitys.add(simpleModel);
            }
        } catch (Exception e) {
            MsLogger.error(e);
            mav.setViewName("/error.jsp");
            return mav;
        }
        mav.addObject("citys", lstCitys);

        // 区/县信息
        List<SimpleModel> lstCountys = new ArrayList<SimpleModel>();
        simpleModel = new SimpleModel();
        simpleModel.setValue(VbConstant.ZonePulldown.ALL);
        simpleModel.setLabel(VbConstant.ZonePulldown.ALL_CITY_NM);
        lstCountys.add(simpleModel);
        mav.addObject("countys", lstCountys);

        // 设置默认全景类型
        poiModel.setPanoramaType("2");
        mav.addObject("poiModel", poiModel);
        mav.setViewName("/add_poi.jsp");
        return mav;
    }

    /**
     * 添加POI基础信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject addPoi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            JSONObject param = this.param2Json(request);
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            param.put("uniqueId", user.getUniqueId());
            JSONObject result = managePoiService.addPoi(param.toString());

            if ("0".equals(result.getString("code"))) {
                return FastJsonUtil.sucess("成功上传");
            }
            return FastJsonUtil.error("-1", result.getString("msg"));

        } catch(IllegalArgumentException e) {
            MsLogger.error(e);
            return FastJsonUtil.error("-2", e.getMessage());
        }
    }

    /**
     * 异步上传图片
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/thumbnail/upload")
    @ResponseBody
    public JSONObject uploadThumbnail(
            @RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();;
        try {
            result = uploadController.uploadFile2Cloud( file, "pic", "video", null, request);

            if ("0".equals(result.getString("code"))) {
                String url = result.getJSONObject("data").getString("access_url");
                result.put("original", file.getOriginalFilename());
                result.put("name", VbUtility.getFileName(url));
                result.put("url", url);
                result.put("size", file.getSize());
                result.put("type", VbUtility.getExtensionOfPicFileName(url));
                result.put("state", "SUCCESS");
                return result;
            }
        } catch (Exception e) {
            MsLogger.error(e);
            result.put("msg", "Failed to upload pic.");
        }
        result.put("original", file.getOriginalFilename());
        result.put("name", file.getOriginalFilename());
        result.put("url", "");
        result.put("size", file.getSize());
        result.put("type", "");
        result.put("state", "FAIL");
        return result;
    }

    /**
     * 异步上传视频
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/video/upload")
    @ResponseBody
    public String uploadVideo(
            @RequestParam(required = true, value = "file") MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String ext = VbUtility.getExtensionOfVideoFileName(file.getOriginalFilename());
            if (ext == null) {
                return FastJsonUtil.error("4", "文件扩展名有错误").toString();
            }
            String fileName = VbMD5.generateToken() + ext;// 生成文件名
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String[] temp = request.getRequestURL().toString().split("/");
            String webAddr = temp[0] + "//" + temp[2] + "/" + temp[3];
            JSONObject result = VODUtil.getInstance().upload2Cloud(file,
                    VODUtil.getVODPoiVideoFolderPath() + "/" + date, fileName, webAddr, 0);

            String phone_url = "";
            JSONObject vodJson = new JSONObject();
            if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                String vod_file_id = data.getString("vod_file_id");
                JSONObject vodResult = VODUtil.getInstance().getVodPlayUrls(vod_file_id);

                if("0".equals(vodResult.getString("code"))){
                    phone_url = vodResult.getJSONObject("data").getString("vod_original_file_url");
                }
                JSONObject param = new JSONObject();
                param.put("vod_file_id", vod_file_id);
                param.put("vod_original_file_url", phone_url);
                vodPlayService.createVodRecord(param.toString());
                vodJson = FastJsonUtil.sucess("成功", param);
            }
            if(!"".equals(phone_url)) {
                vodJson.put("type", VbUtility.getExtensionOfPicFileName(phone_url));
                vodJson.put("state", "SUCCESS");
            } else {
                vodJson.put("type", "");
                vodJson.put("state", "FAIL");
            }
            vodJson.put("url", phone_url);
            vodJson.put("original", file.getOriginalFilename());
            vodJson.put("name", file.getOriginalFilename());
            vodJson.put("size", file.getSize());

            return vodJson.toString();
        } catch (Exception e) {
            JSONObject vodJson = FastJsonUtil.error("-1", "处理过程中系统发生异常:" + VbUtility.printStackTrace(e));
            vodJson.put("original", file.getOriginalFilename());
            vodJson.put("name", file.getOriginalFilename());
            vodJson.put("url", "");
            vodJson.put("size", file.getSize());
            vodJson.put("type", "");
            vodJson.put("state", "FAIL");
            return vodJson.toString();

        }
    }

    /**
     * 1.接收参数检查<p>
     * 2.封装参数到json格式
     * @param request
     * @return
     */
    private JSONObject param2Json(HttpServletRequest request) {
        JSONObject result = managePoiService.downloadPoiTemplete("{}");
        if (!"0".equals(result.get("code"))) {
            return FastJsonUtil.error("-1", result.getString("msg"));
        }
        JSONObject data = result.getJSONObject("data");
        JSONArray privateFieldsDef = data.getJSONArray("privateFieldsDef");

        return PoiCommon.getInstance().param2Json(request, privateFieldsDef, Boolean.FALSE);
    }

}
