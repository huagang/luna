package ms.luna.web.exception;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LunaWebException implements HandlerExceptionResolver {

	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("code", "-1");
		attributes.put("msg", "处理过程中发生异常！");
		view.setContentType("text/html;charset=UTF-8");
		view.setAttributesMap(attributes);
		ModelAndView mav = new ModelAndView();
		mav.setView(view);
		return mav;
	}
}
