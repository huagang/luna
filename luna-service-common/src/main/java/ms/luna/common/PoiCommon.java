package ms.luna.common;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.biz.util.VbUtility;

public class PoiCommon {

	/**
	 * mongo表名
	 * @author Mark
	 *
	 */
	public static final class MongoTable {
		/**
		 * 中文版POI表名
		 */
		public static final String TABLE_POI_ZH = "poi_collection";
		/**
		 * 英文版POI表名
		 */
		public static final String TABLE_POI_EN = "poi_collection_en";
		/**
		 * POI在业务树中使用索引列表
		 */
		public static final String TABLE_POI_USED_INDEX = "poi_business_tree_index";

		/**
		 * 业务树
		 */
		public static final String TABLE_BUSINESS_TREEE = "business_tree";
	}

	/**
	 * Excel相关配置信息
	 * @author Mark
	 *
	 */
	public static final class Excel {

		/**
		 * Excel打开时的默认Zoom值
		 */
		public static final Integer INIT_ZOOM = 80;

		/**
		 * FIXME:(1/3)创建新的共有字段时--->>>需要修改
		 */
		public static final Integer 公有字段个数 = 13;

		/**
		 * FIXME:(2/3)创建新的共有字段时--->>>需要修改<p>
		 * 名称为空的场合以""存在，不允许出现跳过，否则会错乱
		 */
		public static final String[] NM_COL_BASES = new String[] {
				"二级分类",
				"分享摘要",
				"详细介绍",
				"缩略图",
				"全景类型",
				"全景标识",
				"联系电话"
		};
		/**
		 * FIXME:(3/3)创建新的共有字段时--->>>需要修改<p>
		 * 说明为空的场合以""存在，不允许出现跳过，否则会错乱
		 */
		public static final String[] DESC_COL_BASES = new String[] {
				"二级分类名称",
				"POI点的分享摘要",
				"POI点的详细介绍",
				"输入缩略图名称，不能包含中文",
				"全景类别名称，可为空",
				"panoID或者页卡集标识",
				"可为空\r\n：格式：(国家区号)-省市区号-具体号码"
		};
		/**
		 * 模板文件名
		 */
		public static final String 模板文件名称 = "poi_templete.xlsx";
		/**
		 * 模板中的备注内容
		 */
		public static final String 模板Sheet名称 = "Templete_(备注)";

		/**
		 * A列
		 */
		public static final Integer INDEX_COL_A = 0;
		/**
		 * A列名称
		 */
		public static final String NM_COL_A = "名称";
		/**
		 * B列
		 */
		public static final Integer INDEX_COL_B = 1;
		/**
		 * B列名称
		 */
		public static final String NM_COL_B = "别名";
		/**
		 * C列
		 */
		public static final Integer INDEX_COL_C = 2;
		/**
		 * C列名称
		 */
		public static final String NM_COL_C = "纬度";
		/**
		 * D列
		 */
		public static final Integer INDEX_COL_D = 3;
		/**
		 * D列名称
		 */
		public static final String NM_COL_D = "经度";
		/**
		 * E列或EF两列
		 */
		public static final Integer INDEX_COL_E = 4;// index:4-5
		/**
		 * EF列名称
		 */
		public static final String NM_COL_E_F = "地址";
		/**
		 * F列
		 */
		public static final Integer INDEX_COL_F = 5;// index:5
		/**
		 * F列名称
		 */
		public static final String NM_COL_F = "";

		/**
		 * 基准列(强烈建议不动，如果需要增加新字段请参看 FIXME)
		 */
		public static final Integer INDEX_COL_BASE = 6;
	}

	/**
	 * Poi常量信息
	 * @author Administrator
	 *
	 */
	public static final class POI {
		/**
		 * 中文
		 */
		public static final String ZH = "zh";
		/**
		 * 英文
		 */
		public static final String EN = "en";

		public static final Integer 公共TAGID = 1;
		
		public static final Integer TOPTAG_DEFAULT = 8; // 一级分类id默认值为8--其他
		
		public static final Integer PANORAMA_TYPE_DEFAULT = 2; // 全景类型id默认为2

		public static final Integer RADIUS_AROUND_DEFAULT = 1000; // 周边,默认范围

		public static final Integer NUM_AROUND_DEFAULT = 20; // 周边POI数据(由近到远),默认20条

		public static final Integer TAG_ID_TEMPORARY = 63; // 一级分类——临时展位的tag_id

		/*
		 *公共部分 
		 */
		public static final int 名称最大长度 = 64;
		public static final int 别名最大长度 = 64;
		public static final BigDecimal 纬度最小值 = new BigDecimal(-90);
		public static final BigDecimal 纬度最大值 = new BigDecimal(90);
		public static final BigDecimal 经度最小值 = new BigDecimal(-180);
		public static final BigDecimal 经度最大值 = new BigDecimal(180);
		public static final int 详细地址最大长度 = 255;
		public static final int 详细介绍最大长度 = 2048*10;
		public static final int 联系电话最大长度 = 255;
		public static final int 全景数据最大长度 = 255;
		/*
		 * 私有部分
		 */
	}
	private static PoiCommon instance = new PoiCommon();
	private PoiCommon() {
	}
	public static PoiCommon getInstance() {
		return instance;
	}

	/**
	 * 检查POI名称是否合法,如果不合法将返回错误消息<p>
	 * 1.不能为空<br>
	 * 2.长度不能过长<br>
	 * @param param
	 * @return msg
	 */
	public String checkPoiName(String param) {
		if (CharactorUtil.isEmpyty(param)) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0001", "POI名称");
		}
		if (CharactorUtil.countChineseEn(param) > POI.名称最大长度) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0002", "POI名称", POI.名称最大长度);
		}
		return null;
	}

	/**
	 * 检查POI名称是否合法,如果不合法将返回错误消息<p>
	 * 1.不能为空<br>
	 * 2.长度不能过长<br>
	 * @param param
	 * @return msg
	 */
	public String checkPoiName(String[] param) {
		return checkPoiName(param == null || param.length == 0 ? null : param[0]);
	}
	/**
	 * 检查POI别名长度是否合法<p>
	 * 1.长度不能过长
	 * @param param
	 * @return msg
	 */
	public String checkPoiOtherName(String param) {
		if (CharactorUtil.countChineseEn(param) > POI.别名最大长度) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0002", "POI别名", POI.别名最大长度);
		}
		return null;
	}
	/**
	 * 检查POI别名长度是否合法<p>
	 * 1.长度不能过长
	 * @param param
	 * @return msg
	 */
	public String checkPoiOtherName(String[] param) {
		return checkPoiOtherName(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检测POI一级分类是否合法
	 * @param param
	 * @return
	 */
	public String checkPoiTopTag(String param) {
		if("0".equals(param)) {// "0"表示未选择
			return MsLunaMessage.getInstance().getMessage("LUNA.E0017", "一级类别");
		}
		return null;
	}
	
	/**
	 * 检测POI一级分类是否合法
	 * @param param
	 * @return
	 */
	public String checkPoiTopTag(String[] param ) {
		return checkPoiTopTag(param == null || param.length == 0 ? null : param[0]);
	}
	
	/**
	 * 检测POI二级分类是否合法
	 * @param param
	 * @return
	 */
	public String checkPoiSubTag(String param) {
		if("0".equals(param)) {// "0"表示未选择
			return MsLunaMessage.getInstance().getMessage("LUNA.E0017", "二级类别");
		}
		return null;
	}
	
	/**
	 * 检测POI二级分类是否合法
	 * @param param
	 * @return
	 */
	public String checkPoiSubTag(String[] param ) {
		return checkPoiSubTag(param == null || param.length == 0 ? null : param[0]);
	}
	
	/**
	 * 检查纬度是否合法<p>
	 * 1.必须是合法的数字格式<br>
	 * 2.必须在合法的纬度范围内<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkLat(String param) {
		try {
			BigDecimal decimal = new BigDecimal(param).setScale(6, BigDecimal.ROUND_HALF_DOWN);
			if(decimal.compareTo(POI.纬度最大值) > 0 || decimal.compareTo(POI.纬度最小值) < 0) {
				return MsLunaMessage.getInstance().getMessage("LUNA.E0003", "纬度", POI.纬度最小值, POI.纬度最大值);
			}
		} catch (Exception e) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0003", "纬度", POI.纬度最小值, POI.纬度最大值);
		}
		return null;
	}
	/**
	 * 检查纬度是否合法<p>
	 * 1.必须是合法的数字格式<br>
	 * 2.必须在合法的纬度范围内<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkLat(String[] param) {
		return checkLat(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查经度是否合法<p>
	 * 1.必须是合法的数字格式<br>
	 * 2.必须在合法的经度范围内<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkLng(String param) {
		try {
			BigDecimal decimal = new BigDecimal(param).setScale(6, BigDecimal.ROUND_HALF_DOWN);
			if(decimal.compareTo(POI.经度最大值) > 0 || decimal.compareTo(POI.经度最小值) < 0) {
				return MsLunaMessage.getInstance().getMessage("LUNA.E0003", "经度", POI.经度最小值, POI.经度最大值);
			}
		} catch (Exception e) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0003", "经度", POI.经度最小值, POI.经度最大值);
		}
		return null;
	}
	/**
	 * 检查经度是否合法<p>
	 * 1.必须是合法的数字格式<br>
	 * 2.必须在合法的经度范围内<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkLng(String[] param) {
		return checkLng(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查省份是否合法<p>
	 * 1.省份不能为空<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkProvinceId(String param) {
		if (param == null || param.length() == 0) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0001", "省份");
		}
		return null;
	}
	/**
	 * 检查省份是否合法<p>
	 * 1.省份不能为空<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkProvinceId(String[] param) {
		return checkProvinceId(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查详细地址是否合法<p>
	 * 1.详细地址不能过长<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkDetailAddress(String param) {
		if (CharactorUtil.checkPoiDefaultStr(param, POI.详细地址最大长度)) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0002", "详细地址", POI.详细地址最大长度);
		}
		return null;
	}
	/**
	 * 检查详细地址是否合法<p>
	 * 1.详细地址不能过长<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkDetailAddress(String[] param) {
		return checkDetailAddress(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查缩略图地址是否合法<p>
	 * 1.合法的网络地址<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkThumbnail(String param) {
		if (param != null && param.length() != 0 && !VbUtility.checkCOSPicIsOK(param)) {
			return MsLunaMessage.getInstance().getMessage("LUNA.E0004", "缩略图");
		}
		return null;
	}
	/**
	 * 检查缩略图地址是否合法<p>
	 * 1.合法的网络地址<br>
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkThumbnail(String[] param) {
		return checkThumbnail(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查全景数据ID是否合法<p>
	 * 1.不能含有中文字符<br>
	 * 2.不能过长
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkPanoRama(String param) {
		if (param != null && param.length() != 0) {
			if (CharactorUtil.hasChineseChar(param)) {
				return MsLunaMessage.getInstance().getMessage("LUNA.E0004", "全景数据");
			}
			if (CharactorUtil.checkPoiDefaultStr(param, POI.全景数据最大长度)) {
				return MsLunaMessage.getInstance().getMessage("LUNA.E0002", "全景数据");
			}
		}
		return null;
	}
	/**
	 * 检查全景数据ID是否合法<p>
	 * 1.不能含有中文字符<br>
	 * 2.不能过长
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkPanoRama(String[] param) {
		return checkPanoRama(param == null || param.length == 0 ? null : param[0]);
	}

	/**
	 * 检查联系电话是否合法<p>
	 * 1.不能过长
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkContactPhone(String param) {
		if (param != null && param.length() != 0) {
			if (CharactorUtil.checkPoiDefaultStr(param)) {
				return MsLunaMessage.getInstance().getMessage("LUNA.E0002", "联系电话过长");
			}
		}
		return null;
	}

	/**
	 * 检查联系电话是否合法<p>
	 * 1.不能过长
	 * 
	 * @param param
	 * @return msg
	 */
	public String checkContactPhone(String[] param) {
		return checkContactPhone(param == null || param.length == 0 ? null : param[0]);
	}

	
	/**
	 * 检查全景类别是否合法
	 * 
	 * @param param
	 * @return
	 */
	public String checkPanoRamaType(String param){
		return null;
	}
	
	/**
	 * 检查全景类别是否合法
	 * 
	 * @param param
	 * @return
	 */
	public String checkPanoRamaType(String[] param){
		return checkContactPhone(param == null || param.length == 0 ? null : param[0]);
	}
	
	/**
	 * 获取value值
	 * @param paramMaps
	 * @param key
	 * @return
	 */
	private String getValueFromParamMap(Map<String, String[]> paramMaps, String key) {
		String[] values = paramMaps.get(key);
		return (values == null || values.length == 0) ? "" : values[0];
	}

	/**
	 * 检查所有POI字段是否有中文的
	 * @param paramMaps
	 */
	public String checkCommFieldsHasChineseChar(Map<String, String[]> paramMaps) {
		String msg = null;
		if (paramMaps == null) {
			msg = MsLunaMessage.getInstance().getMessage("FW.E0002", "POI参数错误");
			MsLogger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		Boolean hasError = false;
		// 名称
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "longName"));
		// 别名
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "shortName"));
		// 分享摘要
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "shareDesc"));
		// 详细地址
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "detailAddress"));
		// 详细介绍
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "briefIntroduction"));
		// 联系电话
		hasError = hasError || CharactorUtil.hasChineseChar(getValueFromParamMap(paramMaps, "contact_phone"));

		return hasError ? MsLunaMessage.getInstance().getMessage("LUNA.W0001") : null;
	}

	/**
	 * 检查所有POI字段是否有中文的
	 * @param paramMaps
	 */
	public String checkPrivateFieldsHasChineseChar(HttpServletRequest request, JSONArray privateFieldsDef) {
		Map<String, String[]> paramMaps = request.getParameterMap();
		Set<Entry<String, String[]>> set = paramMaps.entrySet();

		Boolean hasError = false;
		Map<String, JSONObject> fieldDefMap = new LinkedHashMap<String, JSONObject>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			if (!field.containsKey("field_def")) {
				continue;
			}
			JSONObject field_def = field.getJSONObject("field_def");
			fieldDefMap.put(field_def.getString("field_name"), field_def);
		}

		// 遍历所有从客户端传过来的键值对
		for (Entry<String, String[]> entry : set) {
			// 无视私有字段以外的值
			if (!fieldDefMap.containsKey(entry.getKey())) {
				continue;
			}
			String value = null;
			// 数组的场合，全部以字符串形式接收
			if (entry.getValue().length > 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < entry.getValue().length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(entry.getValue()[i]);
				}
				value = sb.toString();
			} else {
				value = entry.getValue()[0];
			}
			if (this.checkPrivateFieldHasChineseChar(value, fieldDefMap.get(entry.getKey()))) {
				hasError = true;
				break;
			}
		}
		return hasError ? MsLunaMessage.getInstance().getMessage("LUNA.W0001") : null; 
	}

	/**
	 * 对POI的公共字段做合法性检查
	 * @param paramMaps
	 * @return
	 */
	public void checkCommonFields(Map<String, String[]> paramMaps) {
		String msg = null;

		if (paramMaps == null) {
			msg = MsLunaMessage.getInstance().getMessage("FW.E0002", "POI参数错误");
			throw new IllegalArgumentException(msg);
		}

		// 1.名称
		msg = checkPoiName(paramMaps.get("longName"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
		// 2.别名
		msg = checkPoiOtherName(paramMaps.get("shortName"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}

		// 3.一级类别（topTag）
		msg = checkPoiTopTag(paramMaps.get("topTag"));
		if(msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// 3.二级类别(subTag)
		msg = checkPoiSubTag(paramMaps.get("subTag"));
		if(msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// 4.坐标（lat,lng）
		msg = checkLat(paramMaps.get("lat"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
		// 4.坐标（lat,lng）
		msg = checkLng(paramMaps.get("lng"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}

		// 5.区域地址
		// 省
		msg = checkProvinceId(paramMaps.get("provinceId"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}

		// 市 // 免查
		// 区/县 // 免查

		// 详细地址
		msg = checkDetailAddress(paramMaps.get("detailAddress"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}

		// 6.详细介绍 // 免查

		// 7.缩略图
		msg = checkThumbnail(paramMaps.get("thumbnail"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
		// 8.全景类别：未查
		
		// 8.全景数据ID
		msg = checkPanoRama(paramMaps.get("panorama"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}

		// 9.联系电话
		msg = checkContactPhone(paramMaps.get("contact_phone"));
		if (msg != null) {
			MsLogger.debug(msg);
			throw new IllegalArgumentException(msg);
		}
	}

	private JSONObject convertComField2Json(HttpServletRequest request) {
		JSONObject param = new JSONObject();
		Map<String, String[]> paramMaps = request.getParameterMap();
		// 1.名称
		param.put("long_title", paramMaps.get("longName")[0]);

		// 2.别名
		param.put("short_title", paramMaps.get("shortName")[0]);

		// 3.分享摘要
		param.put("share_desc", paramMaps.get("shareDesc")[0]);
		
		// 3.一级类别（topTag）
		param.put("tags", FastJsonUtil.castStrNumArray2IntNumArray(
				paramMaps.get("topTag")));

		// 3.二级类别(subTag)
		param.put("subTag", Integer.parseInt(paramMaps.get("subTag")[0]));

		// 4.坐标（lat,lng）
		param.put("lat", new BigDecimal(paramMaps.get("lat")[0]).setScale(6, BigDecimal.ROUND_HALF_UP));

		// 4.坐标（lat,lng）
		param.put("lng", new BigDecimal(paramMaps.get("lng")[0]).setScale(6, BigDecimal.ROUND_HALF_UP));

		// 5.区域地址
		// 省
		String[] values = paramMaps.get("provinceId");
		param.put("provinceId", paramMaps.get("provinceId")[0]);
		String zone_id = values[0];

		// 市
		values = paramMaps.get("cityId");
		if (values != null && values.length != 0 && !"ALL".equals(values[0]) && !"".equals(values[0])) {
			param.put("cityId", paramMaps.get("cityId")[0]);
			zone_id = values[0];
		} else {
			param.put("cityId", "ALL");
		}
		// 区/县
		values = paramMaps.get("countyId");
		if (values != null && values.length != 0 && !"ALL".equals(values[0]) && !"".equals(values[0])) {
			param.put("countyId", paramMaps.get("countyId")[0]);
			zone_id = values[0];
		} else {
			param.put("countyId", "ALL");
		}
		param.put("zone_id", zone_id);

		// 详细地址
		values = paramMaps.get("detailAddress");
		if (values == null || values.length == 0) {
			param.put("detail_address", "");
		} else {
			param.put("detail_address", values[0]);
		}

		// 6.简介
		values = paramMaps.get("briefIntroduction");
		if (values == null || values.length == 0) {
			param.put("brief_introduction", "");
		} else {
			param.put("brief_introduction", values[0]);
		}

		// 7.缩略图
		values = paramMaps.get("thumbnail");
		if (values == null || values.length == 0) {
			param.put("thumbnail", "");
		} else {
			param.put("thumbnail", values[0]);
		}

		param.put("thumbnail_1_1", "");
		param.put("thumbnail_16_9", "");

		// 8、全景类型 
		// radiobutton被禁用后无法向form表单传递参数。此处设置了一个临时存取区域
		if(paramMaps.get("panoramaType") == null){
			param.put("panorama_type", paramMaps.get("tempPanoType")[0]);
		} else {
			param.put("panorama_type", paramMaps.get("panoramaType")[0]);
		}
				
		// 8.全景数据ID
		values = paramMaps.get("panorama");
		if (values == null || values.length == 0) {
			param.put("panorama", "");
		} else {
			param.put("panorama", values[0]);
		}

		// 9.联系电话
		values = paramMaps.get("contact_phone");
		if (values == null || values.length == 0) {
			param.put("contact_phone", "");
		} else {
			param.put("contact_phone", values[0]);
		}
		return param;
	}

	/**
	 * 检查私有字段值是否合法(页面输入模式)
	 * @param request
	 * @param privateFieldsDef
	 */
	public void checkPrivateFields(HttpServletRequest request, JSONArray privateFieldsDef) {
		Map<String, String[]> paramMaps = request.getParameterMap();
		Set<Entry<String, String[]>> set = paramMaps.entrySet();

		Map<String, JSONObject> fieldDefMap = new LinkedHashMap<String, JSONObject>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			if (!field.containsKey("field_def")) {
				continue;
			}
			JSONObject field_def = field.getJSONObject("field_def");
			fieldDefMap.put(field_def.getString("field_name"), field_def);
		}

		// 遍历所有从客户端传过来的键值对
		for (Entry<String, String[]> entry : set) {
			// 无视私有字段以外的值
			if (!fieldDefMap.containsKey(entry.getKey())) {
				continue;
			}

			String value = null;
			// 数组的场合，全部以字符串形式接收
			if (entry.getValue().length > 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < entry.getValue().length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(entry.getValue()[i]);
				}
				value = sb.toString();
			} else {
				value = entry.getValue()[0];
			}
			JSONObject privateJson = fieldDefMap.get(entry.getKey());
			if (this.checkPrivateField(value, fieldDefMap.get(entry.getKey()), Boolean.FALSE)) {
				String field_show_name = "";
				if (privateJson != null) {
					field_show_name = privateJson.getString("field_show_name");
				}
				throw new IllegalArgumentException("[" + field_show_name + "] 数据错误，请认真确认数据！");
			}
		}
	}
	/**
	 * 转化请求参数中的私有字段到JSON中
	 * @param request 前段传送过来的Request(含有私有以及共有字段键值对序列，只处理私有字段)
	 * @param privateFieldsDef 私有字段的定义
	 * @param valueIsLabel 数组格式的内容：键和值的区分（键：TRUE,值：FALSE）。
	 * @return JSONObject
	 */
	private JSONObject convertPrivateField2Json(HttpServletRequest request,
			JSONArray privateFieldsDef, Boolean valueIsLabel) {

		JSONObject result = new JSONObject();

		Map<String, String[]> paramMaps = request.getParameterMap();
		Set<Entry<String, String[]>> entrtSet = paramMaps.entrySet();

		Map<String, JSONObject> fieldDefMap = new LinkedHashMap<String, JSONObject>();
		for (int i = 0; i < privateFieldsDef.size(); i++) {
			JSONObject field = privateFieldsDef.getJSONObject(i);
			if (!field.containsKey("field_def")) {
				continue;
			}
			JSONObject field_def = field.getJSONObject("field_def");
			fieldDefMap.put(field_def.getString("field_name"), field_def);
		}

		// 遍历所有从客户端传过来的键值对
		for (Entry<String, String[]> entry : entrtSet) {
			// 无视私有字段以外的值
			if (!fieldDefMap.containsKey(entry.getKey())) {
				continue;
			}

			String value = null;
			// 数组的场合，全部以字符串形式接收
			if (entry.getValue().length > 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < entry.getValue().length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(entry.getValue()[i]);
				}
				value = sb.toString();
			} else {
				value = entry.getValue()[0];
			}
			JSONObject fieldDef = fieldDefMap.get(entry.getKey());
			if (fieldDef == null) {
				continue;
			}

			int field_type = fieldDef.getInteger("field_type");
			int field_size = fieldDef.getInteger("field_size");
			switch (field_type) {
			case VbConstant.POI_FIELD_TYPE.文本框:
				// 整数数字
				if (field_size == -1) {
					result.put(entry.getKey(), Integer.parseInt(value));
				} else {
					result.put(entry.getKey(), value);
				}
				break;

			case VbConstant.POI_FIELD_TYPE.文本域:
				result.put(entry.getKey(), value);
				break;

			case VbConstant.POI_FIELD_TYPE.图片:
				result.put(entry.getKey(), value);
				break;
			case VbConstant.POI_FIELD_TYPE.视频:
				result.put(entry.getKey(), value);
				break;
			case VbConstant.POI_FIELD_TYPE.音频:
				result.put(entry.getKey(), value);
				break;

			case VbConstant.POI_FIELD_TYPE.复选框列表:
				JSONArray array = FastJsonUtil.createBlankIntegerJsonArray();

				JSONArray extension_attr = fieldDef.getJSONArray("extension_attrs");
				value = value.replace(";", ",").replace("；", ",").replace("，", ",");
				String[] vals = value.split("," , extension_attr.size());

				Set<String> set = new HashSet<String>();
				// excel里传是Label标签值的场合
				if (valueIsLabel) {
					Map<String, Integer> map = new LinkedHashMap<String, Integer>();
					for (int i = 0; i < extension_attr.size(); i++) {
						JSONObject json = extension_attr.getJSONObject(i);
						String label = json.getString(String.valueOf(i+1));
						set.add(label);
						map.put(label, i+1);
					}
					for (int i = 0; i < vals.length; i++) {
						if (set.remove(vals[i])) {
							array.add(map.get(vals[i]));
						}
					}
				} else {
					for (int i = 0; i < extension_attr.size(); i++) {
						set.add(String.valueOf(i+1));
					}
					for (int i = 0; i < vals.length; i++) {
						if (set.remove(vals[i])) {
							array.add(Integer.parseInt(vals[i]));
						}
					}
				}
				result.put(entry.getKey(), array);
				break;
			default:
				break;
			}
		}
		return result;
	}
	/**
	 * POI私有字段合法性检查
	 * @param value 校验内容
	 * @param fieldDef 私有字段的定义
	 * @param valueIsLabel 数组格式的内容：键和值的区分（键：TRUE,值：FALSE）。
	 * @return TRUE:有错误，FALSE：无错误
	 */
	public boolean checkPrivateField(String value, JSONObject fieldDef, Boolean valueIsLabel) {
		if (fieldDef == null) {
			return true;
		}
		int field_type = fieldDef.getInteger("field_type");
		int field_size = fieldDef.getInteger("field_size");
		switch (field_type) {
		case VbConstant.POI_FIELD_TYPE.文本框:
			// 整数数字
			if (field_size == -1) {
				return !CharactorUtil.isInteger(value);
			}
			// 字符串
			// 检查长度
			return CharactorUtil.countChineseEn(value) > field_size;

		case VbConstant.POI_FIELD_TYPE.文本域:
			return CharactorUtil.countChineseEn(value) > field_size;

		case VbConstant.POI_FIELD_TYPE.复选框列表:
			if (value == null || value.isEmpty()) {
				return false;
			}
			JSONArray extension_attr = fieldDef.getJSONArray("extension_attrs");
			Set<String> set = new HashSet<String>();
			if (valueIsLabel) {
				for (int i = 0; i < extension_attr.size(); i++) {
					JSONObject json = extension_attr.getJSONObject(i);
					String label = json.getString(String.valueOf(i+1));
					set.add(label);
				}
			} else {
				for (int i = 0; i < extension_attr.size(); i++) {
					set.add(String.valueOf(i+1));
				}
			}
			if ((value.contains(",")||value.contains("，"))
					&& (value.contains(";") || value.contains("；"))) {
				return true;
			}
			// 根据业务要求需要适配中英文的[；]、[;]以及中英文[，]、[,]但是分号和逗号不能同时存在
			value = value.replace(";", ",").replace("；", ",").replace("，", ",");
			String[] vals = value.split("," , extension_attr.size());
			for (int i = 0; i < vals.length; i++) {
				if (!set.remove(vals[i])) {
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	/**
	 * POI私有字段合法性检查
	 * @param value 校验内容
	 * @param fieldDef 私有字段的定义
	 * @param valueIsLabel 数组格式的内容：键和值的区分（键：TRUE,值：FALSE）。
	 * @return TRUE:有错误，FALSE：无错误
	 */
	private boolean checkPrivateFieldHasChineseChar(String value, JSONObject fieldDef) {
		int field_type = fieldDef.getInteger("field_type");
		switch (field_type) {
		case VbConstant.POI_FIELD_TYPE.文本框:
		case VbConstant.POI_FIELD_TYPE.文本域:
			// 字符串
			return CharactorUtil.hasChineseChar(value);
		default:
			return false;
		}
	}

	public JSONObject checkParams(HttpServletRequest request, JSONArray privateFieldsDef) {
		String lang = getValueFromParamMap(request.getParameterMap(), "lang");
		String msg = null;
		try {
			this.checkCommonFields(request.getParameterMap());
			this.checkPrivateFields(request, privateFieldsDef);
			if (POI.EN.equals(lang)) {
				msg = checkCommFieldsHasChineseChar(request.getParameterMap());
				if (msg == null) {
					msg = checkPrivateFieldsHasChineseChar(request, privateFieldsDef);
				}
			}
			if (msg == null) {
				return FastJsonUtil.sucess("no check error");
			}
			return FastJsonUtil.error("-1", msg);
		} catch (IllegalArgumentException iae) {
			return FastJsonUtil.sucess("发生非中文错误");
		}
	}

	/**
	 * POI参数到JSON的转换
	 * @param request
	 * @param result
	 * @return
	 */
	public JSONObject param2Json(HttpServletRequest request, JSONArray privateFieldsDef, Boolean valueIsLabel) {
		this.checkCommonFields(request.getParameterMap());
		this.checkPrivateFields(request, privateFieldsDef);

		JSONObject comFieldJson = this.convertComField2Json(request);
		JSONObject privateFieldJson = this.convertPrivateField2Json(request, privateFieldsDef, valueIsLabel);
		comFieldJson.putAll(privateFieldJson);
		MsLogger.debug(comFieldJson.toJSONString());
		return comFieldJson;
	}
}

