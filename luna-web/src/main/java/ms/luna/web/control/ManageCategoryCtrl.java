package ms.luna.web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.CategoryService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import ms.luna.web.common.PulldownCtrl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component
@Controller
@RequestMapping("/manage/category.do")
public class ManageCategoryCtrl {
//	private static final Logger log = LoggerFactory.getLogger(ManageCategoryCtrl.class);
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PulldownCtrl pulldownCtrl;
	
	/**
	 * 分类页面初始化
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=init_categorys")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				MsLogger.error("session is null");
				return new ModelAndView("/error.jsp");
			}
			session.setAttribute("menu_selected", "category");

			String json = null;
			// categoryService
			JSONObject result = categoryService.getCategorys(json);
			List<Map<String, String>> lstCategorys = new ArrayList<Map<String, String>>();

			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("categorys");
				for (int i = 0; i < arrays.size(); i++) {
					JSONObject categoryJson = arrays.getJSONObject(i);
					Map<String, String> category = new LinkedHashMap<String, String>();
					category.put("row_num", String.valueOf(i));
					category.put("category_id", categoryJson.getString("category_id"));
					category.put("cate_nm_zh", categoryJson.getString("nm_zh"));
					category.put("cate_nm_en", categoryJson.getString("nm_en"));
					category.put("cate_creat_time", categoryJson.getString("cate_creat_time"));
					lstCategorys.add(category);
				}
			}
			Map<String, List<Map<String, String>>> model = new LinkedHashMap<String, List<Map<String, String>>>();
			model.put("lstCategorys", lstCategorys);

			session.setAttribute("lstCategorys", lstCategorys);
			return new ModelAndView("/manage_cate.jsp", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/error.jsp");
	}

	/**
	 * 异步删除某个分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(params = "method=delete_category")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			String category_id = request.getParameter("category_id");
			if (category_id == null || category_id.isEmpty()) {
				response.getWriter().print(JsonUtil.error("-1", "分类ID不能为空"));
				response.setStatus(200);
				return;
			}

			JSONObject param = JSONObject.fromObject("{}");
			param.put("category_id", category_id);

			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");

			// categoryService
			JSONObject result = categoryService.deleteCategory(param.toString(), msUser);

			if ("0".equals(result.getString("code"))) {
				pulldownCtrl.refreshCategorysCache(category_id, "", PulldownCtrl.REFRESHMODE.DELETE);//更新缓存
				response.getWriter().print(JsonUtil.sucess("成功删除"));
				response.setStatus(200);
				return;
			}

			response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
			response.setStatus(200);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(JsonUtil.error("-1", VbUtility.printStackTrace(e)));
			response.setStatus(200);
			return;
		}
	}

	private String checkCategoryNm(String nm_zh, String nm_en) {
		if (nm_zh == null || nm_zh.isEmpty()
				|| nm_en == null || nm_en.isEmpty()) {
			return "类别名称或者类别简称为空";
		}
		if (!CharactorUtil.hasOnlyChineseChar(nm_zh)) {
			return "类别名称只能由汉字组成";
		}
		if (!CharactorUtil.checkLittleAlphaAndNumber(nm_en, new char[]{'_'})) {
			return "类别简称只能由小写字母、数字以及下划线组成";
		}
		if (nm_zh.length() < 2) {
			return "类别名称不能低于超过2个汉字";
		}
		if (nm_zh.length() > 16) {
			return "类别名称不能超过16个汉字";
		}
		if (nm_en.length() < 3) {
			return "类别简称不能低于3个字符";
		}
		if (nm_en.length() > 16) {
			return "类别简称不能超过16个字符";
		}
		
		return null;
	}

	/**
	 * 异步添加分类
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=add_category")
	public void addCategory(HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");
			String category_nm_zh = request.getParameter("category_nm_zh");
			String category_nm_en = request.getParameter("category_nm_en");
			String msg = checkCategoryNm(category_nm_zh, category_nm_en);
			if (msg != null) {
				response.getWriter().print(JsonUtil.error("-1", msg));
				response.setStatus(200);
				return;
			}

			JSONObject param = JSONObject.fromObject("{}");
			param.put("category_nm_zh", category_nm_zh);
			param.put("category_nm_en", category_nm_en);
			HttpSession session = request.getSession(false);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			// categoryService
			JSONObject result = categoryService.addCategory(param.toString(), msUser);
//			if (!"0".equals(result.getString("code"))) {
//				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
//			} else {
//				JSONObject data = (JSONObject) result.get("data");
//				String category_id = data.getString("category_id");
//				pulldownCtrl.refreshCategorysCache(category_id,category_nm_zh,PulldownCtrl.REFRESHMODE.ADD);//更新缓存
//				response.getWriter().print(JsonUtil.sucess("成功创建分类名称："+ category_nm_zh + ", 分类简称:"+category_nm_en));
//			}
			if ("1".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.error("1", "category_nm_zh重名"));
			} else if("2".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.error("2", "category_nm_en重名"));
			} else if("0".equals(result.getString("code"))) {
				JSONObject data = (JSONObject) result.get("data");
				String category_id = data.getString("category_id");
				pulldownCtrl.refreshCategorysCache(category_id,category_nm_zh,PulldownCtrl.REFRESHMODE.ADD);//更新缓存
				response.getWriter().print(JsonUtil.sucess("创建成功"));
			} else {
				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
			}
			
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(JsonUtil.error("-1", "处理过程中系统发生异常:"+VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}

	/**
	 * 异步更新分类
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=update_category")
	public void updateCategory(HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			String category_id = request.getParameter("category_id");
			if (category_id == null || category_id.isEmpty()) {
				response.getWriter().print(JsonUtil.error("-1", "分类ID不能为空"));
				response.setStatus(200);
				return;
			}

			String category_nm_zh = request.getParameter("category_nm_zh");
			String category_nm_en = request.getParameter("category_nm_en");
			String msg = checkCategoryNm(category_nm_zh, category_nm_en);
			if (msg != null) {
				response.setContentType("application/Json; charset=UTF-8");
				response.getWriter().print(JsonUtil.error("-1", msg));
				response.setStatus(200);
				return;
			}

			HttpSession session = request.getSession(false);
			List<Map<String, String>> lstCategorys = (List<Map<String, String>>)session.getAttribute("lstCategorys");
			String old_nm_zh = "";
			String old_nm_en = "";
			for (int i = 0; i < lstCategorys.size(); i++) {
				Map<String, String> map = lstCategorys.get(i);
				if (map.get("category_id").equals(category_id)) {
					old_nm_zh = map.get("cate_nm_zh");
					old_nm_en = map.get("cate_nm_en");
					break;
				}
			}

			if (old_nm_zh.equals(category_nm_zh) && old_nm_en.equals(category_nm_en)) {
				response.getWriter().print(JsonUtil.error("3", "分类名称没有变化"));
				response.setStatus(200);
				return;
			}

			JSONObject param = JSONObject.fromObject("{}");

			param.put("category_id", category_id);
			param.put("category_nm_zh", category_nm_zh);
			param.put("category_nm_en", category_nm_en);
			MsUser msUser = (MsUser)session.getAttribute("msUser");
			// categoryService
			JSONObject result = categoryService.updateCategory(param.toString(), msUser);
//			if (!"0".equals(result.getString("code"))) {
//				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
//			} else {
//				pulldownCtrl.refreshCategorysCache(category_id, category_nm_zh, PulldownCtrl.REFRESHMODE.UPDATE);//更新缓存
//				response.getWriter().print(JsonUtil.sucess("修改成功"));
//			}
			if ("1".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.error("1", "category_nm_zh重名"));
			} else if("2".equals(result.getString("code"))) {
				response.getWriter().print(JsonUtil.error("2", "category_nm_en重名"));
			} else if("0".equals(result.getString("code"))) {
				pulldownCtrl.refreshCategorysCache(category_id, category_nm_zh, PulldownCtrl.REFRESHMODE.UPDATE);//更新缓存
				response.getWriter().print(JsonUtil.sucess("修改成功"));
			} else {
				response.getWriter().print(JsonUtil.error("-1", result.getString("msg")));
			}
			
			response.setStatus(200);
			return;
		} catch (Exception e) {
			try {
				response.getWriter().print(JsonUtil.error("-1", "处理过程中系统发生异常:"+VbUtility.printStackTrace(e)));
				response.setStatus(200);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}
}
