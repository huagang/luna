package ms.luna.web.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class LunaWebException implements HandlerExceptionResolver {

	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		MappingJacksonJsonView view = new MappingJacksonJsonView();
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
