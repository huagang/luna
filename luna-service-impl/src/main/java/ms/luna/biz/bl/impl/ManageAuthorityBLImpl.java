package ms.luna.biz.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.ManageAuthorityBL;
import ms.luna.biz.bl.ManageMerchantBL;
import ms.luna.biz.dao.custom.MsRoleDAO;
import ms.luna.biz.dao.custom.model.AuthorityParameter;
import ms.luna.biz.dao.custom.model.AuthorityResult;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Greek
 * @date create time：2016年3月25日 下午9:36:10
 * @version 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service("ManageAuthorityBL")
public class ManageAuthorityBLImpl implements ManageAuthorityBL {

	@Autowired
	private MsRoleDAO msRoleDAO;

	@Override
	public JSONObject loadAuthority(String json) {
		
		AuthorityParameter authorityParameter = new AuthorityParameter();
		Integer max = null;
		Integer min = null;
		JSONObject param = JSONObject.fromObject(json);
		if (param.has("max")) {
			max = param.getInt("max");
		}
		if (param.has("min")) {
			min = param.getInt("min");
		}
		if (max != null || min != null) {
			authorityParameter.setRange("true");
		}
		if (min == null) {
			min = 0;
		}
		if (max == null) {
			max = Integer.MAX_VALUE;
		}
		authorityParameter.setMin(min);
		authorityParameter.setMax(max);
		
		Integer total = 0;
		List<AuthorityResult> list = null; 
		
		total = msRoleDAO.countAuthority(authorityParameter);
		if (total == 0){
			return JsonUtil.error("-1", "暂时没有数据");
		}
		list = msRoleDAO.selectAuthority(authorityParameter);
		
		JSONObject data = JSONObject.fromObject("{}");
		JSONArray results = JSONArray.fromObject("[]");
		
		for(int i = 0;i<list.size();i++){
			JSONObject result = JSONObject.fromObject("{}");
			result.put("role_code", list.get(i).getRole_code());
			result.put("role_name", list.get(i).getRole_name());
			result.put("module_name", list.get(i).getModule_name());
			result.put("role_type", list.get(i).getRole_type());
			results.add(result);
		}
		
		data.put("total", total);
		data.put("results", results);
		
		return JsonUtil.sucess("数据加载成功！", data); 
	}

}
