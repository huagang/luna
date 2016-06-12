package ms.luna.web.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.sc.ShowPageComponentService;
import ms.luna.web.common.BasicCtrl;

@Component
@Controller
@RequestMapping("/showpage_component.do")
public class ShowPageComponentCtrl extends BasicCtrl {
	private static final Logger logger = Logger.getLogger(ShowPageComponentCtrl.class);
	
	@Autowired
	private ShowPageComponentService showPageComponentService;
	
	public static final String INIT = "method=init";
	public static final String LOAD_COMPONENTS= "method=loadComponents";
	
	
	@RequestMapping(params = INIT)
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = buildModelAndView("showpage_component");
		return modelAndView;
	}
	
	@RequestMapping(params = LOAD_COMPONENTS)
	public void loadComponents(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = loadComponents();
		response.getWriter().print(jsonObject.toString());
	}
	
	public JSONObject loadComponents() {
		JSONObject jsonObject = showPageComponentService.getAllComponent();
		return jsonObject;
	}
	

}
