package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.CategoryBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.CategoryService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryBL categoryBL;
	@Override
	public JSONObject getCategorys(String json) {
		JSONObject result = null;
		try {
			result = categoryBL.getCategorys(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject deleteCategory(String json) {
		JSONObject result = null;
		try {
			result = categoryBL.deleteCategory(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject addCategory(String json) {
		JSONObject result = null;
		try {
			result = categoryBL.addCategory(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject updateCategory(String json) {
		JSONObject result = null;
		try {
			result = categoryBL.updateCategory(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

}
