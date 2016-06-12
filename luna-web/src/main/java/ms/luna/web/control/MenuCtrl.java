package ms.luna.web.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.model.MsUser;
import ms.luna.web.util.WebHelper;

@Component
@Controller
@RequestMapping("/menu.do")
public class MenuCtrl {
	
	@Resource(name="loginCtrl")
	private LoginCtrl loginCtrl;

	@Resource(name="webHelper")
	private WebHelper webHelper;

	@RequestMapping(params = "method=goHome")
	public ModelAndView goHome(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MsUser msUser = (MsUser)session.getAttribute("msUser");
		if (webHelper.hasRoles(msUser,
				VbConstant.Module.Luna.皓月平台高级管理员_Code
				,VbConstant.Module.Luna.皓月平台管理员_Code
				,VbConstant.Module.Luna.皓月平台运营员_Code
				,VbConstant.Module.Vbpano.VBPano管理员_Code
				,VbConstant.Module.Merchant.商家管理员_Code
				,VbConstant.Module.Activity.活动管理员_Code
				,VbConstant.Module.Poi.信息数据管理员_Code)) {
			return new ModelAndView("/manage_user.do?method=init");

		} else if (webHelper.hasRoles(msUser,
				VbConstant.Module.Vbpano.VBPano运营员_Code)) {

			session.invalidate();
			return loginCtrl.goDomainHomeRoot();

		} else if (webHelper.hasRoles(msUser,
				VbConstant.Module.Merchant.商家运营员_Code)) {

			// 微景展管理页
			return new ModelAndView("/manage/app.do?method=init");

		} else if (webHelper.hasRoles(msUser,
				VbConstant.Module.Poi.信息数据运营员_Code)) {

			// POI数据管理页
			return new ModelAndView("/manage_poi.do?method=init");

		} else {
			session.invalidate();
			return loginCtrl.goDomainHomeRoot();
		}
	}

}
