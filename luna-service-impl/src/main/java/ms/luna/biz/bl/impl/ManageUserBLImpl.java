package ms.luna.biz.bl.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import ms.biz.common.AuthenticatedUserHolder;
import ms.luna.biz.bl.ManageUserBL;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.dao.custom.MsBizModuleDAO;
import ms.luna.biz.dao.custom.MsRUserRoleDAO;
import ms.luna.biz.dao.custom.MsRegEmailDAO;
import ms.luna.biz.dao.custom.MsRoleDAO;
import ms.luna.biz.dao.custom.MsUserLunaDAO;
import ms.luna.biz.dao.custom.MsUserPwDAO;
import ms.luna.biz.dao.custom.model.UsersParameter;
import ms.luna.biz.dao.custom.model.UsersResult2;
import ms.luna.biz.dao.model.MsBizModule;
import ms.luna.biz.dao.model.MsRUserRole;
import ms.luna.biz.dao.model.MsRUserRoleCriteria;
import ms.luna.biz.dao.model.MsRegEmail;
import ms.luna.biz.dao.model.MsRegEmailCriteria;
import ms.luna.biz.dao.model.MsRole;
import ms.luna.biz.dao.model.MsRoleCriteria;
import ms.luna.biz.dao.model.MsUserLunaCriteria;
import ms.luna.biz.dao.model.MsUserPw;
import ms.luna.biz.dao.model.MsUserPwCriteria;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.CreateHtmlUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MailSender;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbMD5;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Transactional(rollbackFor = Exception.class)
@Service("manageUserBL")
public class ManageUserBLImpl implements ManageUserBL {

	@Autowired
	private MsBizModuleDAO msBizModuleDAO;

	@Autowired
	private MsRoleDAO msRoleDAO;

	@Autowired
	private MsRegEmailDAO msRegEmailDAO;

	@Autowired
	private MsRUserRoleDAO msRUserRoleDAO;

	@Autowired
	private MsUserPwDAO msUserPwDAO;

	@Autowired
	private MsUserLunaDAO msUserLunaDAO;

	@Override
	public JSONObject loadUsers(String json) {
		JSONObject param = JSONObject.parseObject(json);
		Integer max = null;
		Integer min = null;
		if (param.containsKey("max")) {
			max = param.getInteger("max");
		}
		if (param.containsKey("min")) {
			min = param.getInteger("min");
		}
		UsersParameter usersParameter = new UsersParameter();
		if (max != null || min != null) {
			usersParameter.setRange("true");
		}
		if (min == null) {
			min = 0;
		}
		if (max == null) {
			max = Integer.MAX_VALUE;
		}
		usersParameter.setMax(max);
		usersParameter.setMin(min);

		String categoryId = param.getString("category_id");
		if (!VbConstant.ALL.equals(categoryId)) {
			usersParameter.setCategoryId(categoryId);
		}

		if (param.containsKey("like_filter_nm")) {
			String likeFilterNm = param.getString("like_filter_nm");

//			if (CharactorUtil.checkAlphaAndNumber(likeFilterNm, new char[] { '_' })) {
				likeFilterNm = "%" + likeFilterNm.toLowerCase() + "%";
				usersParameter.setLikeFilterNm(likeFilterNm);
//			}
		}

		String role_code = param.getString("role_code");
		
		MsRole msRole = msRoleDAO.selectByPrimaryKey(role_code);
		String module_code = msRole.getBizModuleCode();
		String role_auth = msRole.getMsRoleAuth();
		usersParameter.setModuleCode(module_code);
		usersParameter.setRoleAuth(role_auth);

		List<UsersResult2> list = null;
		// // 检索总件数
		Integer total = 0;
		//
		// // 件数为0时没有必要做检索
		// if (total > 0) {
		// list = msRUserRoleDAO.selectUsers(usersParameter);
		// }

		// 皓月高级管理员、皓月管理员和皓月运营人员搜索可以查看到更广范围的用户
		if(role_code.equals("luna_senior_admin") || role_code.equals("luna_admin") || role_code.equals("luna_operator")){
			total = msRUserRoleDAO.countUsersByRoleAuth(usersParameter);
			list = msRUserRoleDAO.selectUsersByRoleAuth(usersParameter);
		}else{
			total = msRUserRoleDAO.countUsersByModuleCode(usersParameter);
			list = msRUserRoleDAO.selectUsersByModuleCode(usersParameter);
		}
		JSONObject data = JSONObject.parseObject("{}");
		JSONArray lstUsersResult = JSONArray.parseArray("[]");
		if (list != null) {
			for (UsersResult2 usersResult : list) {
				JSONObject usersResultData = JSONObject.parseObject("{}");
				usersResultData.put("luna_name", usersResult.getLuna_name());
				usersResultData.put("module_code", usersResult.getModule_code());
				usersResultData.put("module_name", usersResult.getModule_name());
				usersResultData.put("role_code", usersResult.getRole_code());
				usersResultData.put("role_name", usersResult.getRole_name());
				lstUsersResult.add(usersResultData);
			}
		}
		data.put("total", total);
		data.put("users", lstUsersResult);
		return FastJsonUtil.sucess("检索成功", data);
	}

	// @Override
	// public JSONObject updateUserRole(String json) {
	// String msg = this.checkAuth(json);
	// if (msg != null) {
	// return FastJsonUtil.error("-1", msg);
	// }
	//
	// JSONObject param = JSONObject.parseObject(json);
	// // 执行操作的用户
	// String login_wjnm = param.getString("login_wjnm");
	//
	// String region_id = param.getString("region_id");
	//
	// // 被执行操作的用户
	// String wjnm = param.getString("wjnm");
	// // 调整后的角色Id
	// String to_role_id = param.getString("to_role_id");
	//
	// Integer loginUserAuth = roleBL.getRoleAuthWithWjNm(login_wjnm);
	//
	// Integer oldUserAuth = roleBL.getRoleAuthWithWjNm(wjnm);
	//
	// Integer newUserAuth = roleBL.getRoleAuthWithRoleId(to_role_id);
	//
	// int count = -1;
	// // 高级管理员
	// if (loginUserAuth == VbConstant.AUTH_GAO_JI_GUAN_LI_YUAN) {
	// // 调整后的regionId
	// VbTrUserRoleRegion vbTrUserRoleRegion = new VbTrUserRoleRegion();
	// vbTrUserRoleRegion.setWjNm(wjnm);
	// vbTrUserRoleRegion.setRoleId(to_role_id);
	// vbTrUserRoleRegion.setDelFlg("0");
	// // String to_region_id = param.getString("to_region_id");
	// if (newUserAuth == VbConstant.AUTH_GAO_JI_GUAN_LI_YUAN || newUserAuth ==
	// VbConstant.AUTH_YOU_KE) {
	// vbTrUserRoleRegion.setRegionId(VbConstant.DUMMY_CODE.REGION_ID);
	// } else {
	// vbTrUserRoleRegion.setRegionId(region_id);
	// }
	// count =
	// vbTrUserRoleRegionDAO.updateByPrimaryKeySelective(vbTrUserRoleRegion);
	// // 普通管理员
	// } else if (loginUserAuth > oldUserAuth && loginUserAuth >= newUserAuth) {
	// // 调整后的regionId
	// VbTrUserRoleRegion vbTrUserRoleRegion = new VbTrUserRoleRegion();
	// vbTrUserRoleRegion.setWjNm(wjnm);
	// vbTrUserRoleRegion.setRoleId(to_role_id);
	// vbTrUserRoleRegion.setDelFlg("0");
	// if (newUserAuth == VbConstant.AUTH_YOU_KE) {
	// vbTrUserRoleRegion.setRegionId(VbConstant.DUMMY_CODE.REGION_ID);
	// } else {
	// vbTrUserRoleRegion.setRegionId(region_id);
	// }
	// count =
	// vbTrUserRoleRegionDAO.updateByPrimaryKeySelective(vbTrUserRoleRegion);
	// // 可以做....
	// } else {
	// throw new RuntimeException("权限不正确，您不能操作！");
	// }
	//
	// if (count != 1) {
	// throw new RuntimeException("您操作的数据不存在！");
	// }
	//
	// return FastJsonUtil.sucess("权限操作成功！");
	// }
	//
	// /**
	// * 权限参数检查
	// *
	// * @param json
	// * @return
	// */
	// private String checkAuth(String json) {
	// JSONObject param = JSONObject.parseObject(json);
	// if (!param.containsKey("login_wjnm") || !param.containsKey("country_id") ||
	// !param.containsKey("province_id") || !param.containsKey("city_id")
	// || !param.containsKey("category_id") || !param.containsKey("region_id") ||
	// !param.containsKey("to_role_id")
	// || !param.containsKey("wjnm")) {
	// return "数据不正确,缺少参数！";
	// }
	// String login_wjnm = param.getString("login_wjnm");
	// String country_id = param.getString("country_id");
	// String province_id = param.getString("province_id");
	// String city_id = param.getString("city_id");
	// String category_id = param.getString("category_id");
	// String region_id = param.getString("region_id");
	// String to_role_id = param.getString("to_role_id");
	// String wjnm = param.getString("wjnm");
	//
	// if (login_wjnm.isEmpty() || country_id.isEmpty() || province_id.isEmpty()
	// || city_id.isEmpty()
	// || category_id.isEmpty() || region_id.isEmpty() || to_role_id.isEmpty()
	// || wjnm.isEmpty()) {
	// return "数据不正确，有值为空！";
	// }
	//
	// Integer loginUserAuth = roleBL.getRoleAuthWithWjNm(login_wjnm);
	//
	// if (loginUserAuth == null) {
	// System.out.println("用户权限数据不对,微景名称：" + login_wjnm);
	// return "用户权限数据不对,微景名称：" + login_wjnm;
	// }
	//
	// if (loginUserAuth == VbConstant.AUTH_YOU_KE) {
	// return "游客不允许做操作";
	// }
	//
	// Integer oldUserAuth = roleBL.getRoleAuthWithWjNm(wjnm);
	// if (oldUserAuth == null) {
	// System.out.println("用户权限数据不对,微景名称：" + wjnm);
	// return "用户权限数据不对,微景名称：" + wjnm;
	// }
	//
	// Integer newUserAuth = roleBL.getRoleAuthWithRoleId(to_role_id);
	// if (newUserAuth == null) {
	// System.out.println("用户权限数据不对,角色Id：" + to_role_id);
	// return "用户权限数据不对,角色Id：" + to_role_id;
	// }
	//
	// if (newUserAuth != VbConstant.AUTH_GAO_JI_GUAN_LI_YUAN && newUserAuth !=
	// VbConstant.AUTH_YOU_KE) {
	// if (VbConstant.ALL.equals(country_id) ||
	// VbConstant.ALL.equals(province_id)
	// || VbConstant.ALL.equals(city_id) || VbConstant.ALL.equals(category_id)
	// || VbConstant.ALL.equals(region_id)) {
	// return "请指定具体区域！";
	// }
	//
	// VbRegion vbRegion = vbRegionDAO.selectByPrimaryKey(region_id);
	// if (vbRegion == null || !vbRegion.getCityId().equals(city_id)) {
	// return "区域信息不正确！";
	// }
	//
	// VbCategory vbCategory = vbCategoryDAO.selectByPrimaryKey(category_id);
	// if (vbCategory == null) {
	// return "分类信息不正确！";
	// }
	//
	// // 城市信息
	// MsZone msZone = msZoneDAO.selectByPrimaryKey(city_id);
	// if (msZone == null || VbConstant.DEL_FLG.删除.equals(msZone.getDelFlg())
	// || !msZone.getParentId().equals(province_id)) {
	// return "城市信息不正确";
	// }
	// msZone = msZoneDAO.selectByPrimaryKey(msZone.getParentId());
	// if (msZone == null || VbConstant.DEL_FLG.删除.equals(msZone.getDelFlg())
	// || !msZone.getParentId().equals(country_id)) {
	// return "省份信息不正确";
	// }
	// msZone = msZoneDAO.selectByPrimaryKey(msZone.getParentId());
	// if (msZone == null || VbConstant.DEL_FLG.删除.equals(msZone.getDelFlg())) {
	// return "国家信息不正确";
	// }
	// }
	// return null;
	// }

	public JSONObject loadRolesByModulecode(String json) {
		// 根据module_code得到对应的role_code和role_name
		JSONObject param = JSONObject.parseObject(json);
		String module_code = param.getString("module_code");

		MsUser msUser = (MsUser) AuthenticatedUserHolder.get();
		String role_code = msUser.getMsRoleCode();
		MsRole msRole = msRoleDAO.selectByPrimaryKey(role_code);
		String role_auth = msRole.getMsRoleAuth();
		String biz_module_code = msRole.getBizModuleCode();
		
		MsRoleCriteria msRoleCriteria = new MsRoleCriteria();
		MsRoleCriteria.Criteria criteria = msRoleCriteria.createCriteria();
		criteria.andBizModuleCodeEqualTo(module_code);
		
		if(role_code.equals("luna_admin") || role_code.equals("luna_operator")){
			if(module_code.equals(biz_module_code)){// 例如：greek作为luna管理员时，选中业务模块“luna平台”，此时下拉列表不能含有“高级管理员”
				criteria.andMsRoleAuthGreaterThanOrEqualTo(role_auth);
			}
		}
			
		List<MsRole> list = null;
		list = msRoleDAO.selectByCriteria(msRoleCriteria);

		if(list == null){
			throw new RuntimeException("不存在该module_code,module_code:" + module_code);
		}
		
        Collections.sort(list, new Comparator<MsRole>() {
            public int compare(MsRole arg0, MsRole arg1) {
                return arg0.getMsRoleAuth().compareTo(arg1.getMsRoleAuth());
            }
        });
		
		JSONObject result = JSONObject.parseObject("{}");
		JSONArray roles = JSONArray.parseArray("[]");
		for (int i = 0; i < list.size(); i++) {
			MsRole msRole2 = list.get(i);
			JSONObject role = JSONObject.parseObject("{}");
			role.put("role_code", msRole2.getMsRoleCode());
			role.put("short_role_name", msRole2.getShortRoleName());
			roles.add(role);
		}
		result.put("roles", roles);
		return FastJsonUtil.sucess("0", result);

	}

	/*
	 * code 0:成功 1:邮箱(s)已注册
	 */
	@Override
	public JSONObject inviteUsers(String json) {
		MsUser msUser = (MsUser) AuthenticatedUserHolder.get();
		String luna_nm = msUser.getNickName();

		JSONObject param = JSONObject.parseObject(json);
		String[] emails = param.getString("emails").split(";");
		String module_code = param.getString("module_code");
		String role_code = param.getString("role_code");
		String webAddr = param.getString("webAddr");

		// 先邮箱名合法性检测.如果合法,写入数据库
		// valid条件：1、未被注册使用 2、邮件注册链接过期未用 3、邮件未过期但也未使用
		List<String> invalEmaillst = new ArrayList<>();
		List<String> valEmaillst = new ArrayList<>();
		List<String> tokenlst = new ArrayList<>();

		String module_nm = "";
		String role_nm = "";

		for (String email : emails) {
			if (!checkMail(email)) {
				invalEmaillst.add(email);
				continue;
			}
			valEmaillst.add(email);
		}

		JSONObject result = JSONObject.parseObject("{}");
		// 邮箱检测
		if (!invalEmaillst.isEmpty()) {
			result.put("code", "1");
			result.put("msg", "邮箱已注册");
			result.put("data", invalEmaillst.toString());
			MsLogger.debug("邮箱已注册，email:"+valEmaillst.toString());
			return result;
		}
		// 插入DB(ms_reg_email)
		for (String email : valEmaillst) {
			Date registHhmmss = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String token = VbMD5.generateToken() + sdf.format(registHhmmss);
			String status = "0"; // "0":待注册"1"：已注册"2"：注册时间过期

			MsRegEmail msRegEmail = new MsRegEmail();
			msRegEmail.setEmail(email);
			msRegEmail.setBizModuleCode(module_code);
			msRegEmail.setMsRoleCode(role_code);
			msRegEmail.setStatus(status);
			msRegEmail.setToken(token);
			msRegEmail.setRegistHhmmss(registHhmmss);
			msRegEmailDAO.insert(msRegEmail);

			tokenlst.add(token);
		}
		// 获得module_nm
		MsBizModule msBizModule = msBizModuleDAO.selectByPrimaryKey(module_code);
		module_nm = msBizModule.getBizModuleName();

		// 获得role_nm
		MsRole msRole = msRoleDAO.selectByPrimaryKey(role_code);
		role_nm = msRole.getMsRoleName();

		// 获得当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String currentDate = sdf.format(new Date());

		// 发送邮件
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < valEmaillst.size(); i++) {
			executorService.execute(new MailRunnable(valEmaillst.get(i), tokenlst.get(i), module_nm, currentDate,
					luna_nm, role_nm, webAddr));
			MsLogger.debug("邮件发送成功！mail:"+valEmaillst.get(i));
		}
		executorService.shutdown();

		return FastJsonUtil.sucess("发送成功");
	}

	@Override
	public JSONObject updateUser(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String luna_name = param.getString("luna_name");
		// String module_code = param.getString("module_code");
		String role_code = param.getString("role_code");

		// 根据luna_name查找ms_user_pw表获取unique_id
		MsUserPwCriteria msUserPwCriteria = new MsUserPwCriteria();
		MsUserPwCriteria.Criteria criteria = msUserPwCriteria.createCriteria();
		criteria.andLunaNameEqualTo(luna_name);
		List<MsUserPw> userlst = msUserPwDAO.selectByCriteria(msUserPwCriteria);
		if (userlst.size() == 1) {// 正常情况下luna_name唯一
			MsUserPw msUserPw = userlst.get(0);
			String unique_id = msUserPw.getUniqueId();
			MsRUserRoleCriteria msRUserRoleCriteria = new MsRUserRoleCriteria();
			MsRUserRoleCriteria.Criteria userCriteria = msRUserRoleCriteria.createCriteria();
			userCriteria.andUniqueIdEqualTo(unique_id);

			MsRUserRole msRUserRole = new MsRUserRole();
			msRUserRole.setMsRoleCode(role_code);
			// 根据unique_id更新role_code
			Integer rows = msRUserRoleDAO.updateByCriteriaSelective(msRUserRole, msRUserRoleCriteria);
			if (rows == 1) {// 正常情况下unique_id唯一
				return FastJsonUtil.sucess("更新成功！");
			} else {
				throw new RuntimeException("数据库异常！unique_id不唯一，unique_id："+ unique_id);
			}
		} else {
			throw new RuntimeException("数据库异常！luna_name 不唯一，luna_name:"+ luna_name);
		}
	}

	@Override
	public JSONObject delUser(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String luna_name = param.getString("luna_name");
		// String module_code = param.getString("module_code");
		// String role_code = param.getString("role_codes");

		// 根据luna_name查找ms_user_pw表获取unique_id
		MsUserPwCriteria msUserPwCriteria = new MsUserPwCriteria();
		MsUserPwCriteria.Criteria criteria = msUserPwCriteria.createCriteria();
		criteria.andLunaNameEqualTo(luna_name);
		List<MsUserPw> userlst = null;
		userlst = msUserPwDAO.selectByCriteria(msUserPwCriteria);
		if (userlst != null) {
			MsUserPw msUserPw = userlst.get(0);
			String unique_id = msUserPw.getUniqueId();
			String email = msUserPw.getEmail();
			// 删除ms_user_pw表数据
			msUserPwDAO.deleteByCriteria(msUserPwCriteria);

			// 删除ms_r_user_role表数据
			MsRUserRoleCriteria msRUserRoleCriteria = new MsRUserRoleCriteria();
			MsRUserRoleCriteria.Criteria userRoleCriteria = msRUserRoleCriteria.createCriteria();
			userRoleCriteria.andUniqueIdEqualTo(unique_id);
			msRUserRoleDAO.deleteByCriteria(msRUserRoleCriteria);
			MsLogger.debug("删除ms_r_user_role表数据，unique_id："+unique_id);
			
			// 删除ms_user_luna表数据
			MsUserLunaCriteria msUserLunaCriteria = new MsUserLunaCriteria();
			MsUserLunaCriteria.Criteria userLunaCriteria = msUserLunaCriteria.createCriteria();
			userLunaCriteria.andUniqueIdEqualTo(unique_id);
			msUserLunaDAO.deleteByCriteria(msUserLunaCriteria);
			MsLogger.debug("删除ms_user_luna表数据，unique_id："+unique_id);
			
			// 删除ms_reg_eamil表数据
			if(email != null){
				MsRegEmailCriteria msRegEmailCriteria = new MsRegEmailCriteria();
				MsRegEmailCriteria.Criteria msRegCriteria = msRegEmailCriteria.createCriteria();
				msRegCriteria.andEmailEqualTo(email);
				msRegEmailDAO.deleteByCriteria(msRegEmailCriteria);
				MsLogger.debug("删除ms_reg_eamil表数据，email："+email);
			}
			
			return FastJsonUtil.sucess("删除成功!");
		} else {
			throw new RuntimeException("用户不存在，luna_name:"+luna_name);
		}
		
	}

	/**
	 * 邮箱名合法性检测
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkMail(String email) {

		// 检测email是否使用过
		MsRegEmailCriteria msRegEmailCriteria1 = new MsRegEmailCriteria();
		MsRegEmailCriteria.Criteria criteria1 = msRegEmailCriteria1.createCriteria();
		criteria1.andEmailEqualTo(email);
		Integer num1 = msRegEmailDAO.countByCriteria(msRegEmailCriteria1);

		if (num1 == 0) {// email未使用过
			return true;
		}

//		// 检测链接是否过期
//		List<MsRegEmail> lstMsRegEmail = msRegEmailDAO.selectByCriteria(msRegEmailCriteria1);
//		MsRegEmail msRegEmail = lstMsRegEmail.get(0);
//		Date registTime = msRegEmail.getRegistHhmmss();
//		Date currentTime = new Date();
//
//		if (currentTime.getTime() - registTime.getTime() > RegistBLImpl.VALIDINTERVAL) {
//			msRegEmailDAO.deleteByCriteria(msRegEmailCriteria1);// 链接过期直接删除
//			return true;
//		}
		
		// 只检测是否注册，未被注册，不管是否过期仍然可使用
		List<MsRegEmail> lstMsRegEmail = msRegEmailDAO.selectByCriteria(msRegEmailCriteria1);
		MsRegEmail msRegEmail = lstMsRegEmail.get(0);
		String status = msRegEmail.getStatus();
		if(status.equals("0")){
			msRegEmailDAO.deleteByCriteria(msRegEmailCriteria1);//每一次重新邀请，都把之前的信息先删除再添加
			return true;
		}
		
		// 已被注册
		return false;
	}

	@Override
	public JSONObject getModuleByRoleCode(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String role_code = param.getString("role_code");
		// 根据role_code找到module_code;
		MsRole msRole = msRoleDAO.selectByPrimaryKey(role_code);
		String module_code = msRole.getBizModuleCode();
		// 根据module_code找到module_nm;
		MsBizModule msBizModule = msBizModuleDAO.selectByPrimaryKey(module_code);
		String module_nm = msBizModule.getBizModuleName();

		JSONObject result = JSONObject.parseObject("{}");
		result.put("module_code", module_code);
		result.put("module_nm", module_nm);
		return FastJsonUtil.sucess("业务模块信息获取成功！", result);
	}

	/**
	 * code:0 -- success(得到相对应的auth) code:1 -- 没有对应的auth
	 */
	@Override
	public JSONObject getAuthByRoleCodeAndModuleCode(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String role_code = param.getString("role_code");
		String module_code = param.getString("module_code");
		MsRoleCriteria msRoleCriteria = new MsRoleCriteria();
		MsRoleCriteria.Criteria criteria = msRoleCriteria.createCriteria();
		criteria.andMsRoleCodeEqualTo(role_code).andBizModuleCodeEqualTo(module_code);
		List<MsRole> lst = null;
		lst = msRoleDAO.selectByCriteria(msRoleCriteria);
		if (lst.size() != 1) {
			return FastJsonUtil.error("1", "没有对应的auth,role_code:" + role_code + ", module_code:" + module_code);
		}
		
		MsRole msRole = lst.get(0);
		String role_auth = msRole.getMsRoleAuth();
		JSONObject data = JSONObject.parseObject("{}");
		data.put("role_auth", role_auth);
		return FastJsonUtil.sucess("success", data);
	}

	@Override
	public JSONObject getAuthByRoleCode(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String role_code = param.getString("role_code");
		MsRole msRole = msRoleDAO.selectByPrimaryKey(role_code);
		String role_auth = msRole.getMsRoleAuth();
		JSONObject data = JSONObject.parseObject("{}");
		data.put("role_auth", role_auth);
		return FastJsonUtil.sucess("success", data);
	}

	class MailRunnable implements Runnable {

		String HOST = "smtp.exmail.qq.com"; // 服务器
		String PORT = "25"; // 端口号
		String USERNAME = "luna@visualbusiness.com"; // 用户名
		String PASSWORD = "Vb12345"; // 邮箱密码
		String FROMADDRESS = "luna@visualbusiness.com"; // 邮箱名（发件人）
		String SUBJECT = "皓月平台-账户激活"; // 主题
		boolean VALIDATE = true;

		private String toAddress; // 发送地址
		private String token; // 链接token
		private String module_nm; // 业务模块
		private String currentDate; // 当前日期
		private String luna_nm; // 邀请人
		private String role_nm; // 权限名称
		private String webAddr; // 包含域名的web地址

		public MailRunnable(String toAddress, String token, String module_nm, String currentDate, String luna_nm,
				String role_nm, String webAddr) {
			this.toAddress = toAddress;
			this.token = token;
			this.module_nm = module_nm;
			this.currentDate = currentDate;
			this.luna_nm = luna_nm;
			this.role_nm = role_nm;
			this.webAddr = webAddr;
		}

		@Override
		public void run() {
			try{
				MailSender.MailProperty mail = new MailSender().new MailProperty();
	
				mail.setMailServerHost(HOST);
				mail.setMailServerPort(PORT);
				mail.setValidate(VALIDATE);
				mail.setUserName(USERNAME);
				mail.setPassword(PASSWORD);
				mail.setFromAddress(FROMADDRESS);
				mail.setSubject(SUBJECT);
				mail.setToAddress(toAddress);
				String content = CreateHtmlUtil.getInstance().convert2EmailHtml(toAddress, token, module_nm, currentDate,
						luna_nm, role_nm, webAddr);
				mail.setContent(content);
	
				MailSender send = new MailSender();
				send.sendHtmlMail(mail);
			} catch (Exception e){
				MsLogger.error("邮件发送出现异常，email:" + toAddress + e);
			}
		}
	}
// ps:在Java中，线程方法的异常（无论是checked还是unchecked exception），都应该在线程代码边界之内（run方法内）进行try catch并处理掉.
}
