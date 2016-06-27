package ms.luna.web.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ms.luna.web.common.BasicCtrl;
import ms.luna.biz.util.MsLogger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: wumengqiang
 * @email: dean@visualbusiness.com
 * @Date: June 16, 2016
 *
 *
 *
 */

@Component
@Controller
@RequestMapping("/show_poi.do")
public class ShowPoiDetail extends BasicCtrl{
	
	public static final String INIT = "method=init";
	
	@RequestMapping(params = INIT)
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView();
		try{			
			view.setViewName("/show_poi.jsp");
			return view;
		} catch(Exception e){
			MsLogger.error(e);
			view.setViewName("/error.jsp");
			return view;
		}
	}
}	

