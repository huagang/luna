package ms.luna.web.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.sc.ManageAuthorityService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Greek
 * @date create time：2016年3月25日 下午8:45:31
 * @version 1.0
 */
@Component
@Controller
@RequestMapping("/authority.do")
public class AuthorityCtrl {

	@Autowired
	private ManageAuthorityService manageAuthorityService;

	/**
	 * 页面初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "method=init_authority")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute("menu_selected", "authority");
			}
			return new ModelAndView("/authority.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/error.jsp");
	}

	/**
	 * 页面初始化
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("method=async_search_groups")
	public void asyncSearchGroups(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html; charset=UTF-8");

			JSONObject param = JSONObject.fromObject("{}");
			if (offset != null) {
				param.put("min", offset);
			}
			if (limit != null) {
				param.put("max", limit);
			}
			param.put("min", offset);
			param.put("max", limit);

			JSONObject resJSON = JSONObject.fromObject("{}");

			JSONObject result = manageAuthorityService.loadAuthority(param.toString());
			if ("0".equals(result.getString("code"))) {
				JSONObject data = result.getJSONObject("data");
				JSONArray arrays = data.getJSONArray("results");

				Integer total = data.getInt("total");
				JSONArray rows = JSONArray.fromObject("[]");

				for (int i = 0; i < arrays.size(); i++) {
					JSONObject row = JSONObject.fromObject("{}");
					row.put("role_code", arrays.getJSONObject(i).getString("role_code"));
					row.put("role_name", arrays.getJSONObject(i).getString("role_name"));
					row.put("module_name", arrays.getJSONObject(i).getString("module_name"));
					row.put("role_type", arrays.getJSONObject(i).getString("role_type"));
					rows.add(row);
				}
				resJSON.put("total", total);
				resJSON.put("rows", rows);

			} else {
				resJSON.put("total", 0);
			}
			response.getWriter().print(resJSON.toString());
			response.setStatus(200);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject resJSON = JSONObject.fromObject("{}");
		resJSON.put("total", 0);
		response.getWriter().print(resJSON.toString());
		response.setStatus(200);
		return;
	}
}
