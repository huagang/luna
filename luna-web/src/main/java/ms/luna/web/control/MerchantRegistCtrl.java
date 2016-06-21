package ms.luna.web.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbMD5;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.common.PulldownCtrl;

/**
 * @author Greek
 * @date create time：2016年4月12日 下午3:50:38
 * @version 1.0
 */
@Component
@Controller
@RequestMapping("/merchantRegist.do")
public class MerchantRegistCtrl extends BasicCtrl {

	private static final String MERREGIST_URI = "/merchant-register.jsp";
	private static final String SUCCESS_URI = "/merchant_register_success.jsp";

	@Autowired
	private PulldownCtrl pulldownCtrl;

	@Autowired
	private ManageMerchantCtrl manageMerchantCtrl;

	@Autowired
	private ManageMerchantService manageMerchantService;

	@RequestMapping(params = "method=init_regPage")
	public ModelAndView init_regist(@RequestParam(required = false) Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			ModelAndView model = new ModelAndView(MERREGIST_URI, map);
			model.addObject("categoryMap", pulldownCtrl.loadCategorys());
			model.addObject("provinces", pulldownCtrl.loadProvinces());
			return model;
		} catch (Exception e) {
			return new ModelAndView("/error.jsp", map);
		}
	}
	
	/**
	 * 商户注册
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=regist")
	public void createMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
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
			String county_id = request.getParameter("county");// 区县id
			String resource_content = request.getParameter("resource_content");// 证件

			String inputInfo = checkInput(contact_nm, contact_phonenum, contact_mail, merchant_nm, merchant_phonenum, category_id, resource_content, province_id, city_id, county_id, merchant_addr, lat, lng, merchant_info);
			// 输入校验通过
//			String inputInfo = "";
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
				param.put("lat", lat);
				param.put("lng", lng);
				param.put("merchant_info", merchant_info);
				if(!county_id.equals("ALL")){
					param.put("county_id", county_id);
				}
				if(!resource_content.equals("")){
					param.put("resource_content", resource_content);
				}
				
				JSONObject result = manageMerchantService.createMerchant(param.toString());
				MsLogger.debug("method:createMerchant, result from service: "+result.toString());
				
				String code = result.getString("code");
				if ("0".equals(code)) {
					response.getWriter().print(FastJsonUtil.sucess("编辑成功！"));
				} else if ("1".equals(code)) {
					response.getWriter().print(FastJsonUtil.error("3", "用户重名！"));
				} else if ("2".equals(code)) {
					response.getWriter().print(FastJsonUtil.error("4", "业务员不存在！"));
				} else {
					response.getWriter().print(FastJsonUtil.error("-1", "编辑失败！"));
				}
			} else {
				response.getWriter().print(FastJsonUtil.error("1", "校验错误！"));
			}
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "Failed to regist merchant: " + e));
		}
		response.setStatus(200);
		return;
	}
	

	/**
	 * 检查商户名称是否存在（新建商户）
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=checkNm")
	public void checkMerchantNm_add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			String merchant_nm = request.getParameter("merchant_nm");
			JSONObject param = JSONObject.parseObject("{}");
			param.put("merchant_nm", merchant_nm);
			JSONObject result = manageMerchantService.isAddedMerchantNmEist(param.toString());
			MsLogger.debug("method:isAddedMerchantNmEist, result from service: "+result.toString());
			if (result.getString("code").equals("1")) {
				result.put("code", "0");
			} else if (result.getString("code").equals("0")) {
				result.put("code", "1");
			}
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "Failed to check merchant name:" + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
			return;
		}
	}

	/**
	 * 注册成功页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=success")
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		return new ModelAndView(SUCCESS_URI);
	}

	/**
	 * 异步上传图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=upload_thumbnail")
	public void uploadThumbnail(@RequestParam(required = true, value = "thumbnail_fileup") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try{
			
			String orignalFileName = file.getOriginalFilename();
			String ext = VbUtility.getExtensionOfPicFileName(orignalFileName);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = "/"+sdf.format(date) + "/" + VbMD5.generateToken() + ext;
			byte[] bytes = file.getBytes();// 获得文件内容
			JSONObject result = COSUtil.getInstance().uploadLocalFile2Cloud(COSUtil.LUNA_BUCKET, bytes,
					localServerTempPath, COSUtil.getLunaCRMRoot() + fileName);// 上传
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(FastJsonUtil.error("-1", "Failed to upload thumbnail: " + VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				MsLogger.error(e1);
			}
			return;
		}
		//super.uploadLocalFile2Cloud(request, response, file, COSUtil.picAddress);
	}
	
	/**
	 * 输入检查
	 * 
	 * @return
	 */
	private String checkInput(String contact_nm, String contact_phonenum, String contact_mail,
			String merchant_nm, String merchant_phonenum, String category_id, String resource_content, String province_id,
			String city_id, String county_id, String merchant_addr, String lat,String lng, String merchant_info) {

		String inputInfo = checkContactNm(contact_nm) + checkMerchantNm(merchant_nm)
				+ checkCategoryId(category_id) + checkResourceContent(resource_content) + checkProvinceId(province_id)
				+ checkCityId(city_id) + checkCountyId(county_id) + checkMerchantAddr(merchant_addr) + checkLatAndLng(lat, lng);

		if (!CharactorUtil.checkEmail(contact_mail)) {
			inputInfo = inputInfo + "邮件输入有误！";
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
	 * 经纬度检测
	 * 
	 * @param lng
	 * @return
	 */
	private String checkLatAndLng(String lat, String lng) {
		if (lat == null || lat.isEmpty()) {
			return "纬度有误";
		}
		if (lng == null || lng.isEmpty()) {
			return "经度有误";
		}
		// 纬度检测
		boolean hasError = false;
		String regPattern = "([+-]?\\d{1,3})(\\.\\d{0,6})";
		if (Pattern.matches(regPattern,lat)) {
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
//		if (county_id == null || county_id.length() == 0 || county_id.equals("ALL"))
		if (county_id == null || county_id.length() == 0 )//允许区/县不选择
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
		if (merchant_addr == null || merchant_addr.length() == 0)
			return "商户地址有误！";
		return "";
	}
	
	/**
	 * 测试
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "method=test")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String contact_nm = "NMTEST"; // 联系人名字
//		String contact_phonenum = "13999999999";// 联系人手机
//		String contact_mail = "china@qq.com";// 联系人邮箱
//		String merchant_nm = "MERCHANTTEST";// 商户名字
//		String merchant_phonenum = "010-99999999";// 商户电话
//		String category_id = "2H2n1A250G2c1c0u3w0q1k2t2X3H0f05";// 业务种类
//		String resource_content = "http://view.luna.visualbusiness.cn/dev/uploadfiles3v0X0e24370y3E3K3K3v2k1Q0T0-1t1n.jpg";// 证件
//		String province_id = "110000";// 省份id
//		String city_id = "110100";// 城市id
//		String county_id = "110101";// 区县id
//		String merchant_addr = "北京邮电大学";// 商户地址
//		String merchant_info = "";// 商户介绍
//		String lat = "39.95826";// 经纬度
//		String lng = "116.35741";
//		
//		JSONObject param = JSONObject.parseObject("{}");
//		param.put("contact_nm", contact_nm);
//		param.put("contact_phonenum", contact_phonenum);
//		param.put("contact_mail", contact_mail);
//		param.put("merchant_nm", merchant_nm);
//		param.put("merchant_phonenum", merchant_phonenum);
//		param.put("category_id", category_id);
//		param.put("resource_content", resource_content);
//		param.put("province_id", province_id);
//		param.put("city_id", city_id);
//		param.put("county_id", county_id);
//		param.put("merchant_addr", merchant_addr);
//		param.put("merchant_info", merchant_info);
//		param.put("lat", lat);
//		param.put("lng", lng);
//		
//		//创建测试
////		JSONObject result = manageMerchantService.createMerchant(param.toString());
//		//加载测试
////		param.put("merchant_id", "MERCH000000001");
////		JSONObject result = manageMerchantService.loadMerchantById(param.toString());
//		//编辑测试
//		param.put("merchant_id", "1k3J1_3P2T3J0q2i392L0S0-1v212c3c");
//		param.put("salesman_nm", "greek");
//		param.put("status_id", "1");
//		JSONObject result = manageMerchantService.isEditedMerchantNmEist(param.toString());
//		System.out.println(result.toString());
//		if(result.getString("code").equals("1")){
//			result = manageMerchantService.updateMerchantById(param.toString());
//			System.out.println(result.toString());
//		}
//		response.getWriter().print(result);
//		response.setStatus(200);
		return;
	}
}
