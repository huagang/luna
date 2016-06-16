package ms.luna.biz.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualbusiness.gennum.service.GenNumService;

import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.DateUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.dao.custom.MsCategoryDAO;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;
import ms.luna.biz.bl.CategoryBL;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Transactional(rollbackFor=Exception.class)
@Service("categoryBL")
public class CategoryBLImpl implements CategoryBL {
	@Autowired
	private GenNumService genNumService;

	@Autowired
	private MsCategoryDAO msCategoryDAO;
	@Override
	public JSONObject getCategorys(String json) {
		MsCategoryCriteria msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria()
		.andDelFlgEqualTo("0");
		msCategoryCriteria.setOrderByClause("REGIST_HHMMSS DESC");
		List<MsCategory> list = msCategoryDAO.selectByCriteria(msCategoryCriteria);
		JSONObject result = JSONObject.parseObject("{}");
		JSONObject data = JSONObject.parseObject("{}");
		result.put("code", "0");
		JSONArray categorys = JSONArray.parseArray("[]");
		if (list != null && !list.isEmpty()) {
			for (MsCategory vbCategory : list) {
				JSONObject record = JSONObject.parseObject("{}");
				record.put("category_id", vbCategory.getCategoryId());
				record.put("nm_zh", vbCategory.getNmZh());
				record.put("nm_en", vbCategory.getNmEn());
				record.put("cate_creat_time", DateUtil.format(vbCategory.getRegistHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
				categorys.add(record);
			}
			result.put("msg", "查找到数据:"+list.size() + "件");
		}
		data.put("categorys", categorys);
		result.put("data", data);
		return result;
	}

	@Override
	public JSONObject deleteCategory(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String categoryId = param.getString("category_id");
		int count = msCategoryDAO.deleteByPrimaryKey(categoryId);
		JSONObject result = JSONObject.parseObject("{}");
		if (count == 1) {
			result.put("code", "0");
			result.put("msg", "正常删除："+categoryId);
		} else {
			result.put("code", "0");
			result.put("msg", "已经不存在："+categoryId);
		}
		return result;
	}

	@Override
	public JSONObject addCategory(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String category_nm_zh = param.getString("category_nm_zh");
		String category_nm_en = param.getString("category_nm_en");
		MsCategoryCriteria msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria()
		.andNmZhEqualTo(category_nm_zh);
		int count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 0) {
//			return FastJsonUtil.error("-1", "重名,不可用："+category_nm_zh);
			return FastJsonUtil.error("1", "category_nm_zh重名");
		}

		msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria()
		.andNmEnEqualTo(category_nm_en);
		count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 0) {
//			return FastJsonUtil.error("-2", "重名,不可用："+category_nm_en);
			return FastJsonUtil.error("2", "category_nm_en重名");
		}

		String num = genNumService.generateNum("CATE", 1L, 16);
		if (CharactorUtil.isEmpyty(num)) {
			MsLogger.debug("编号生成失败：[CATE]");
			throw new RuntimeException("编号生成失败：[CATE]");
		}
		MsCategory vbCategory = new MsCategory();
		vbCategory.setCategoryId(num);
		vbCategory.setNmZh(category_nm_zh);
		vbCategory.setNmEn(category_nm_en);
		msCategoryDAO.insertSelective(vbCategory);

		msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria()
		.andNmZhEqualTo(category_nm_zh);
		count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 1) {
			MsLogger.debug("重名,不可用(下手慢了)："+category_nm_zh);
			throw new RuntimeException("重名,不可用(您下手慢了)："+category_nm_zh);
		}
		msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria()
		.andNmEnEqualTo(category_nm_en);
		count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 1) {
			MsLogger.debug("重名,不可用(下手慢了)："+category_nm_en);
			throw new RuntimeException("重名,不可用(您下手慢了)："+category_nm_en);
		}
		JSONObject data = JSONObject.parseObject("{}");
		data.put("category_id", num);
		return FastJsonUtil.sucess("创建成功！",data);
	}

	@Override
	public JSONObject updateCategory(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String categoryId = param.getString("category_id");
		String category_nm_zh = param.getString("category_nm_zh");
		String category_nm_en = param.getString("category_nm_en");

		// 检查修改后的category_nm_zh是否存在
		MsCategoryCriteria msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria().andNmZhEqualTo(category_nm_zh).andCategoryIdNotEqualTo(categoryId);
		int count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 0) {
			return FastJsonUtil.error("1", "category_nm_zh重名");
		}
		// 检查修改后的category_nm_en是否存在
		msCategoryCriteria = new MsCategoryCriteria();
		msCategoryCriteria.createCriteria().andNmEnEqualTo(category_nm_en).andCategoryIdNotEqualTo(categoryId);
		count = msCategoryDAO.countByCriteria(msCategoryCriteria);
		if (count > 0) {
			return FastJsonUtil.error("2", "category_nm_en重名");
		}
		
		MsCategory vbCategory = new MsCategory();
		vbCategory.setCategoryId(categoryId);
		vbCategory.setNmZh(category_nm_zh);
		vbCategory.setNmEn(category_nm_en);
		count = msCategoryDAO.updateByPrimaryKeySelective(vbCategory);

		if (count != 1) {
			return FastJsonUtil.sucess("不存在："+category_nm_zh);
		}
		return FastJsonUtil.sucess("更新成功！");
	}

}
