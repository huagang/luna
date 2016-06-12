package ms.luna.web.control.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.MsShowPageService;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.control.ShowPageCtrl;
import ms.luna.web.filter.LoginFilter;

@Component
@Controller
@RequestMapping("/api/showpage.do")
public class ShowPageApiCtrl extends BasicCtrl {

	public static final String GET_ONE_PAGE_DETAIL = "method=getOnePageDetail";
	public static final String GET_ALL_PAGE_DETAIL = "method=getAllPageDetail";
	
	@Autowired
	private ShowPageCtrl appCtrl;
	
	@RequestMapping(params = GET_ALL_PAGE_DETAIL)
	public void getAllPageDetail(@RequestParam(required=true, value="app_id") int appId,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws IOException {
		
		appCtrl.getAllPageDetail(appId, request, response);

	}
	
	@RequestMapping(params = GET_ONE_PAGE_DETAIL)
	public void getOnePageDetail(@RequestParam(required=true, value="page_id") String pageId,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws IOException {
		
		appCtrl.getOnePageDetail(pageId, request, response);
	}
	
	
}
