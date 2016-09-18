package ms.luna.web.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import ms.luna.biz.util.MsLogger;

public class LunaWebException implements HandlerExceptionResolver {

	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		MsLogger.error(ex);
		// 根据不同错误转向不同页面  
		if (ex instanceof MaxUploadSizeExceededException) {
			MappingJacksonJsonView view = new MappingJacksonJsonView();
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("code", "-1");
			attributes.put("msg", "文件大小已经超过限制！");
			view.setContentType("text/html;charset=UTF-8");
			view.setAttributesMap(attributes);
			ModelAndView mav = new ModelAndView();
			mav.setView(view);
			return mav;

		} else {
			return new ModelAndView("error.jsp", model);
		}
	}
}
