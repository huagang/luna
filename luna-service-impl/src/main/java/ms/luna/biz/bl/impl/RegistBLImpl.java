package ms.luna.biz.bl.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualbusiness.gennum.service.GenNumService;

import ms.luna.biz.bl.RegistBL;
import ms.luna.biz.dao.custom.MsRUserRoleDAO;
import ms.luna.biz.dao.custom.MsRegEmailDAO;
import ms.luna.biz.dao.custom.MsUserLunaDAO;
import ms.luna.biz.dao.custom.MsUserPwDAO;
import ms.luna.biz.dao.model.MsRUserRole;
import ms.luna.biz.dao.model.MsRegEmail;
import ms.luna.biz.dao.model.MsRegEmailCriteria;
import ms.luna.biz.dao.model.MsUserLuna;
import ms.luna.biz.dao.model.MsUserLunaCriteria;
import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.util.JsonUtil;
import ms.luna.biz.util.VbMD5;
import net.sf.json.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年4月7日 下午6:16:27 
 * @version 1.0 
 */
@Transactional(rollbackFor=Exception.class)
@Service("registBLImpl")
public class RegistBLImpl implements RegistBL{

	@Autowired
	private MsRegEmailDAO msRegEmailDAO;
	
	@Autowired
	private GenNumService genNumService;
	
	@Autowired
	private MsUserLunaDAO msUserLunaDAO;
	
	@Autowired
	private MsUserPwDAO msUserPwDAO;
	
	@Autowired
	private MsRUserRoleDAO msRUserRoleDAO;
	
	public static long VALIDINTERVAL = 3*24*60*60*1000;// 邮件链接有效期3天
	
	@Override
	public JSONObject registPwUser(String json) {
		
		JSONObject param = JSONObject.fromObject(json);
		String nickname = param.getString("nickname");
		String password = param.getString("password");
		String token = param.getString("token");
		// 检查是否被注册。注册前有链接的检查isTokenValid()，但仍可能出现多个人共用一个链接
		// 同时进行注册时，出现抢占的情况
		MsRegEmailCriteria msRegEmailCriteria = new MsRegEmailCriteria();
		MsRegEmailCriteria.Criteria criteria = msRegEmailCriteria.createCriteria();
		criteria.andTokenEqualTo(token).andStatusEqualTo("0");
		List<MsRegEmail> lstMsRegEmail = null;
		lstMsRegEmail = msRegEmailDAO.selectByCriteria(msRegEmailCriteria);
		if(lstMsRegEmail == null){
			return JsonUtil.error("1", "该账户已被注册");
		}
		
		// 乐观锁——status
		MsRegEmail msRegEmail = new MsRegEmail();
		msRegEmail.setStatus("1");
		Integer rows = msRegEmailDAO.updateByCriteriaSelective(msRegEmail, msRegEmailCriteria);
		if(rows != 1){
			return JsonUtil.error("1", "该账户已被注册");
		}
		
		msRegEmail = lstMsRegEmail.get(0);
		String role_code = msRegEmail.getMsRoleCode();
		String module_code = msRegEmail.getBizModuleCode();
		String email = msRegEmail.getEmail();
		
		return regist(nickname,password,role_code,module_code,email);
	}

	/**
	 * 注册账户
	 * @param nickname账户名
	 * @param password密码
	 * @param role_code
	 * @param module_code
	 * @return
	 */
	private JSONObject regist(String nickname, String password, String role_code, String module_code, String email) {
		// 涉及DB表ms_user_luna，ms_user_pw，ms_r_user_role
		String unique_id = genNumService.generateNum("REGI");
		
		// unique_id检测(不需要进行id是否重复的检测，generateNum在生成时已经保证id不可能重复)
		if(unique_id == null){
			throw new RuntimeException("id生成失败");
		}
		
		// ms_user_luna
		MsUserLuna msUserLuna = new MsUserLuna();
		msUserLuna.setUniqueId(unique_id);
		msUserLuna.setStatus("0");
		msUserLunaDAO.insertSelective(msUserLuna);
		
		// ms_user_pw
		String md5Pw = VbMD5.convertFixMD5Code(password);
		MsUserPw msUserPw = new MsUserPw();
		msUserPw.setUniqueId(unique_id);
		msUserPw.setLunaName(nickname);
		msUserPw.setPwLunaMd5(md5Pw);
		msUserPw.setEmail(email);
		msUserPwDAO.insertSelective(msUserPw);

		// ms_r_user_role
		MsRUserRole msRUserRole = new MsRUserRole();
		msRUserRole.setMsRoleCode(role_code);
		msRUserRole.setUniqueId(unique_id);
		msRUserRoleDAO.insertSelective(msRUserRole);
		
		return JsonUtil.sucess("账户创建成功！");
	}

	@Override
	public JSONObject isTokenValid(String json) {
		JSONObject param = JSONObject.fromObject(json);
		String token = param.getString("token");
		MsRegEmailCriteria msRegEmailCriteria = new MsRegEmailCriteria();
		MsRegEmailCriteria.Criteria criteria = msRegEmailCriteria.createCriteria();
		criteria.andTokenEqualTo(token).andStatusEqualTo("0");
		Integer count = msRegEmailDAO.countByCriteria(msRegEmailCriteria);
		if(count == 0){
			return JsonUtil.error("-2", "无有效链接！");//可能没有,也可能已注册
		}
		
		List<MsRegEmail> lstMsRegEmail = msRegEmailDAO.selectByCriteria(msRegEmailCriteria);
		MsRegEmail msRegEmail = lstMsRegEmail.get(0);
		Date registTime = msRegEmail.getRegistHhmmss();
		Date currentTime = new Date();
		// 链接过期检测
		if(currentTime.getTime() - registTime.getTime()> VALIDINTERVAL){
			msRegEmailDAO.deleteByCriteria(msRegEmailCriteria);//链接过期直接删除
			return JsonUtil.error("-3", "链接过期！");
		}
		return JsonUtil.sucess("存在有效链接！");
	}

}
