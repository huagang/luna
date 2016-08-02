package ms.luna.web.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.sc.MsShowPageService;
import ms.luna.biz.sc.ShowPageComponentService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.util.RequestHelper;

@Component
@Controller
@RequestMapping("/app.do")
public class ShowPageCtrl extends BasicCtrl {
	private static final Logger logger = Logger.getLogger(ShowPageCtrl.class);
	
	@Autowired
	private ManageShowAppService msShowAppService;

	@Autowired
	private MsShowPageService msShowPageService;
	
	@Autowired
	private ShowPageComponentService showPageComponentService;
	
	public static final String INIT = "method=init";
	public static final String ADD_NEW_PAGE = "method=addNewBlankPage";
	public static final String SAVE_PAGES = "method=savePages";
	public static final String GET_ONE_PAGE_DETAIL = "method=getOnePageDetail";
	public static final String GET_ALL_PAGE_SUMMARY = "method=getAllPageSummary";
	public static final String GET_ALL_PAGE_DETAIL = "method=getAllPageDetail";
	public static final String DELETE_PAGE = "method=deletePage";
	public static final String UPDATE_PAGE_ORDER = "method=updatePageOrder";
	public static final String PREVIEW = "method=preview";
	public static final String SAVE_SETTING_OF_APP = "method=saveSettingOfApp";
	public static final String GET_SETTING_OF_APP = "method=getSettingOfApp";
	public static final String MODIFY_PAGE_NAME = "method=modifyPageName";
	public static final String PUBLISH_APP = "method=publishApp";
	public static final String EXIST_ONLINE_APP = "method=existOnlineApp";

	/**
	 * 分类页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = INIT)
	public ModelAndView init(@RequestParam("app_id") int appId, HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView modelAndView = buildModelAndView("show_page");
			modelAndView.addObject("appId", appId);
			return modelAndView;

		} catch (Exception e) {
			logger.error("Failed to load all pages", e);;
		}
		return new ModelAndView("/error.jsp");
	}

	/**
	 * 保存单页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = SAVE_PAGES)
	public void updateShowPage(
			@RequestParam(required=true, value="data") String pages,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		if (StringUtils.isBlank(pages)) {
			response.getWriter().print(FastJsonUtil.error("-1", "page数据不能为空！"));
			response.setStatus(200);
			return;
		}

		try {
			MsUser msUser = (MsUser)request.getSession(false).getAttribute("msUser");
			JSONObject result = msShowPageService.updatePages(pages, msUser);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			response.getWriter().print(FastJsonUtil.error("-1", "更新页面失败"));
			logger.error("Failed to save page", e);
			response.setStatus(200);
		}
	}

	/**
	 * 修改页面名称
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = MODIFY_PAGE_NAME)
	public void modifyPageName(
			@RequestParam(required=true, value="app_id") int appId,
			@RequestParam(required=true, value="page_id") String pageId,
			@RequestParam(required=true, value="page_name") String pageName,
			@RequestParam(required=true, value="page_code") String pageCode,
			@RequestParam(required=true, value="page_type" ) int pageType,
			@RequestParam(required=true, value="page_height" ) int pageHeight,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		if(appId <= 0) {
			response.getWriter().print(FastJsonUtil.error("-1", "appId不合法！"));
			response.setStatus(200);
			return;
		}
		
		if(StringUtils.isBlank(pageId)) {
			response.getWriter().print(FastJsonUtil.error("-1", "pageId不能为空！"));
			response.setStatus(200);
			return;
		}
		
		if (StringUtils.isBlank(pageName)) {
			response.getWriter().print(FastJsonUtil.error("-1", "页面名称不能为空！"));
			response.setStatus(200);
			return;
		}
		if (!CharactorUtil.hasOnlyChineseChar(pageName)) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称只能由汉字组成！"));
			response.setStatus(200);
			return;
		}
		if (pageName.length() < 2) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称不能少于2个汉字！"));
			response.setStatus(200);
			return;
		}
		if (pageName.length() > 32) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称不能超过32个汉字！"));
			response.setStatus(200);
			return;
		}
		if (StringUtils.isBlank(pageCode)) {
			response.getWriter().print(FastJsonUtil.error("-1", "页面code不能为空！"));
			response.setStatus(200);
			return;
		}
		if (!CharactorUtil.checkAlphaAndNumber(pageCode, new char[]{})) {
			response.getWriter().print(FastJsonUtil.error("-1", "code只能由半角数字和小写字母组成！"));
			response.setStatus(200);
			return;
		}
		if (pageCode.getBytes().length < 2) {
			response.getWriter().print(FastJsonUtil.error("-1", "code不能少于2个字符！"));
			response.setStatus(200);
			return;
		}
		if (pageCode.getBytes().length > 32) {
			response.getWriter().print(FastJsonUtil.error("-1", "code不能超过32个字符！"));
			response.setStatus(200);
			return;
		}

		JSONObject params = new JSONObject();
		params.put("app_id", appId);
		params.put("page_id", pageId);
		params.put("page_name", pageName);
		params.put("page_code", pageCode);
		params.put("page_type", pageType);
		params.put("page_height", pageHeight);
		try {
			MsUser msUser = (MsUser)request.getSession(false).getAttribute("msUser");
			JSONObject result = msShowPageService.updatePageName(params.toString(), msUser);
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			logger.error("Failed to modify page name", e);
			response.getWriter().print(FastJsonUtil.error("-1", "更新页面名称失败"));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 添加新的空页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = ADD_NEW_PAGE)
	public void addNewBlankPage(
			@RequestParam(required=true, value="app_id") int appId,
			@RequestParam(required=true, value="page_name") String pageName,
			@RequestParam(required=true, value="page_code") String pageCode,
			@RequestParam(required=true, value="page_order") int pageOrder,
			@RequestParam(required=true, value="page_type" ) int pageType,
			@RequestParam(required=true, value="page_height" ) int pageHeight,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		

		if (StringUtils.isBlank(pageName)) {
			response.getWriter().print(FastJsonUtil.error("-1", "页面名称不能为空！"));
			response.setStatus(200);
			return;
		}
		if (!CharactorUtil.hasOnlyChineseChar(pageName)) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称只能由汉字组成！"));
			response.setStatus(200);
			return;
		}
		if (pageName.length() < 2) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称不能少于2个汉字！"));
			response.setStatus(200);
			return;
		}
		if (pageName.length() > 32) {
			response.getWriter().print(FastJsonUtil.error("-1", "名称不能超过32个汉字！"));
			response.setStatus(200);
			return;
		}
		if (StringUtils.isBlank(pageCode)) {
			response.getWriter().print(FastJsonUtil.error("-1", "页面code不能为空！"));
			response.setStatus(200);
			return;
		}
		if (!CharactorUtil.checkAlphaAndNumber(pageCode, new char[]{})) {
			response.getWriter().print(FastJsonUtil.error("-1", "code只能由半角数字和小写字母组成！"));
			response.setStatus(200);
			return;
		}
		if (pageCode.getBytes().length < 2) {
			response.getWriter().print(FastJsonUtil.error("-1", "code不能少于2个字符！"));
			response.setStatus(200);
			return;
		}
		if (pageCode.getBytes().length > 32) {
			response.getWriter().print(FastJsonUtil.error("-1", "code不能超过32个字符！"));
			response.setStatus(200);
			return;
		}

		JSONObject params = new JSONObject();
		params.put("app_id", appId);
		params.put("page_name", pageName);
		params.put("page_code", pageCode);
		params.put("page_order", pageOrder);
		params.put("page_type", pageType);
		params.put("page_height", pageHeight);
		try {
			MsUser msUser = (MsUser)request.getSession(false).getAttribute("msUser");
			JSONObject result = msShowPageService.createOnePage(params.toString(), msUser);
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			logger.error("Failed to add new page", e);
			response.getWriter().print(FastJsonUtil.error("-1", "添加页面失败"));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 删除页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = DELETE_PAGE)
	public void deletePage(
			@RequestParam(required=true, value="page_id") String pageId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			JSONObject result = msShowPageService.deletePageById(pageId);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to delete page", e);
			response.getWriter().print(FastJsonUtil.error("-1", "删除页面失败"));
			response.setStatus(200);
		}
	}
	
	

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = PREVIEW)
	public void preview(@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			JSONObject result = msShowAppService.generateShowApp(appId);
			response.getWriter().print(result.toString());
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to preview", e);
			response.getWriter().print(FastJsonUtil.error("-1", "预览失败"));
			response.setStatus(200);
		}
	}

	/**
	 * 保存单页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = GET_ONE_PAGE_DETAIL)
	public void getOnePageDetail(
			@RequestParam(required=true, value="page_id") String pageId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		if (StringUtils.isBlank(pageId)) {
			response.getWriter().print(FastJsonUtil.error("1", "没有pageId!"));
			response.setStatus(200);
			return;
		}

		try {
			JSONObject result = msShowPageService.getOnePageDetail(pageId);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to get one page detail", e);
			response.getWriter().print(FastJsonUtil.error("-1", "获取单页面详情失败"));
			response.setStatus(200);
		}
	}
	
	@RequestMapping(params = GET_ALL_PAGE_SUMMARY)
	public void getAllPageSummary(
			@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			JSONObject result = msShowPageService.getAllPageSummary(appId);
			logger.debug(result.toJSONString());
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to get all page summary", e);
			response.getWriter().print(FastJsonUtil.error("-1", "请求所有页面基本信息失败"));
			response.setStatus(200);
		}
	}
	
	@RequestMapping(params = GET_ALL_PAGE_DETAIL)
	public void getAllPageDetail(
			@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			JSONObject result = msShowPageService.getAllPageDetail(appId);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to get all page detail", e);
			response.getWriter().print(FastJsonUtil.error("-1", "请求所有页面详情失败"));
			response.setStatus(200);
		}
	}
	
	@RequestMapping(params = UPDATE_PAGE_ORDER)
	public void updatePageOrder(
			@RequestParam(required=true, value="data") String pageOrder,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		try {
			
			JSONObject result = msShowPageService.updatePageOrder(pageOrder);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to update page order", e);
			response.getWriter().print(FastJsonUtil.error("-1", "更新页面顺序失败"));
			response.setStatus(200);
		}
	}

	/**
	 * 保存单页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = GET_SETTING_OF_APP)
	public void getSettingOfApp(
			@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		try {
			JSONObject result = msShowAppService.getSettingOfApp(appId);
			response.getWriter().print(result);
			response.setStatus(200);
			return;
		} catch (Exception e) {
			logger.error("Failed to get app setting", e);
			response.getWriter().print(FastJsonUtil.error("-1", "获取页面设置失败"));
			response.setStatus(200);
			return;
		}
	}

	/**
	 * 保存单页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = SAVE_SETTING_OF_APP)
	public void saveSettingOfApp(
			@RequestParam(required=true, value="app_id") String app_id,
			@RequestParam(required=false, value="pic_thumb") String pic_thumb,
			@RequestParam(required=false, value="app_name") String app_name,
			@RequestParam(required=false, value="note") String note,
			@RequestParam(required=false, value="share_info_pic") String share_info_pic,
			@RequestParam(required=false, value="share_info_title") String share_info_title,
			@RequestParam(required=false, value="share_info_des") String share_info_des,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");

		if (app_id == null || app_id.isEmpty()) {
			response.getWriter().print(FastJsonUtil.error("1", "没有appId!"));
			response.setStatus(200);
			return;
		}
		HttpSession session = request.getSession(false);
		try {
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			JSONObject param = new JSONObject();
			param.put("app_id", app_id);
			param.put("pic_thumb", CharactorUtil.nullToBlank(pic_thumb));
			param.put("app_name", CharactorUtil.nullToBlank(app_name));
			param.put("note", CharactorUtil.nullToBlank(note));
			param.put("share_info_pic", CharactorUtil.nullToBlank(share_info_pic));
			param.put("share_info_title", CharactorUtil.nullToBlank(share_info_title));
			param.put("share_info_des", CharactorUtil.nullToBlank(share_info_des));
			JSONObject result = msShowAppService.saveSettingOfApp(param.toString(), msUser);
			response.getWriter().print(result);
			response.setStatus(200);
		} catch (Exception e) {
			logger.error("Failed to save app setting");
			response.getWriter().print(FastJsonUtil.error("-1", "保存页面设置失败"));
			response.setStatus(200);
		}
	}
	
	@RequestMapping(params = EXIST_ONLINE_APP)
	public void existOnlineApp(@RequestParam(required=true, value="app_id") int appId,
				HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		try{
			JSONObject ret = msShowAppService.existOnlineApp(appId);
			response.getWriter().print(ret);
		} catch (Exception e) {
			logger.error("Failed to check if app online", e);
			response.getWriter().print(FastJsonUtil.error(-1, "检查微景是否在线失败"));
		}
	}
	
	@RequestMapping(params = PUBLISH_APP)
	public void publishApp(@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		int forceFlag = RequestHelper.getInteger(request, "force");
		JSONObject param = new JSONObject();
		param.put("app_id", appId);
		if(forceFlag == 1) {
			param.put("force", 1);
		}
		try{
			JSONObject ret = msShowAppService.publishApp(param.toString());
			response.getWriter().print(ret);
		} catch (Exception e) {
			logger.error("Failed to publish app", e);
			response.getWriter().print(FastJsonUtil.error(-1, "发布失败"));
		}
	}
}
