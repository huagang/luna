package ms.luna.web.control;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ms.luna.biz.sc.LoginService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;
import net.sf.json.JSONObject;

@Component
@Controller
@RequestMapping("/luna_api.do")
public class POICtrl {
	@Autowired
	// private LoginService loginService;

	// 根据业务和一级分类获取poi数据
	@RequestMapping(params = "method=getPoisByBusinessIdAndTagId", method = RequestMethod.POST)
	public void getPoisBy(@RequestParam(required = true) String bussiness_id,
			@RequestParam(required = true) String tag_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=UTF-8");
		
		
		
		return;
	}
}
