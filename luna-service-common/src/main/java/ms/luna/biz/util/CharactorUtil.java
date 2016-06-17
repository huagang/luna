/**
 * 
 */
package ms.luna.biz.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ms.luna.biz.cons.VbConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
public class CharactorUtil {
	
	public static BigDecimal MaxLat = new BigDecimal(90);
	public static BigDecimal MinLat = new BigDecimal(-90);
	public static BigDecimal MaxLng = new BigDecimal(180);
	public static BigDecimal MinLng = new BigDecimal(-180);
	
	public static boolean isEmpyty(String src) {
		return src == null || src.isEmpty();
	}
	public static String nullToBlank(String src) {
		return src == null ? "": src;
	}

	public static String nullToALL(String src) {
		return src == null || src.isEmpty() ? VbConstant.ALL: src;
	}

	public static String blankRegionNmToNoneBlank(String roleAuth, String regionNm) {
		int auth = Integer.parseInt(roleAuth);
		if (auth == VbConstant.AUTH_GAO_JI_GUAN_LI_YUAN) {
			return VbConstant.ALL_REGION_NM;
		} else if (auth == VbConstant.AUTH_YOU_KE) {
			return VbConstant.REGION_NM_YOU_KE;
		}
		return nullToBlank(regionNm);
	}
	
	public static boolean hasChineseChar(String str){
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]+"); 
		Matcher m = p.matcher(str); 
		if(m.find()){ 
			temp = true;
		}
		return temp;
	}
	
	public static int countChineseEn(String str){
		if (str == null) {
			return 0;
		}
		String cn = str.replaceAll("[^\u4e00-\u9fa5]", "");
		return str.length() + cn.length(); 
	}
	
	public static boolean hasOnlyChineseChar(String str){
		if (str == null || str.isEmpty()) {
			return false;
		}
		String regex = "[\u4E00-\u9FA5]+";
		return str.matches(regex);
	}

	public static boolean isInteger(String str){
		if (str == null || str.isEmpty()) {
			return true;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static JSONObject checkPw(String pw) {
		JSONObject result = JSONObject.parseObject("{}");
		
		// 密码长度检查
		if (!CharactorUtil.checkLength(pw, 6, 20)) {
			result.put("code", "201");
			result.put("msg", "密码长度错误。请不少于6位，并且不多于20位。");
			return result;
		}
		// 密码字符检查
		if (!CharactorUtil.checkAlphaAndNumber(pw, new char[]{'_',
				'!',
				'!',
				'@',
				'#',
				'$',
				'%',
				'^',
				'&',
				'*',
				'(',
				')',
				'-',
				'=',
				'<',
				'>',
				',',
				'.',
				'?',
				':',
				'{',
				'}',
				'|',
				'\\',
				'\"',
				'\'',
				'~',
		'`'})) {
			result.put("code", "-302");
			result.put("msg", "密码设置过程中，存在非法字符！");
			return result;
		}
		result.put("code", "0");
		result.put("msg", "密码可以设置");
		return result;
	}

	public static JSONObject checkNickNm(String wjnm) {
		JSONObject result = JSONObject.parseObject("{}");
		// 用户名长度检查
		if (!CharactorUtil.checkLength(wjnm, 4, 20)) {
			result.put("code", "201");
			result.put("msg", "用户名长度错误。请不少于3位，并且不多于20位。");
			return result;
		}
		// 用户名字符检查
//		if (!CharactorUtil.checkLittleAlphaAndNumber(wjnm, new char[]{'_','-'})) {
//			result.put("code", "202");
//			result.put("msg", "用户名设置过程中，存在非法字符！");
//			return result;
//		}
		result.put("code", "0");
		result.put("msg", "用户名合法");
		return result;
	}

	public static String toLowCase(String src) {
		return src == null? null : src.toLowerCase();
	}
	public static boolean checkLength(String src, int minLen, int maxLen) {
		if (src == null) {
			return false;
		}
		int length = countChineseEn(src);
		return length >= minLen && length <= maxLen;
	}
	public static String trim(String src) {
		if (src == null) {
			return null;
		}
		src = src.trim();
		int start = 0;
		for (int i = 0; i < src.length(); i++) {
			if (' ' == src.charAt(i) && start == 0) {
				continue;
			}
			start = i;
			break;
		}
		int end = src.length();
		for (int i = src.length()-1; i >= 0 ; i--) {
			if (' ' == src.charAt(i) && end == src.length()) {
				continue;
			}
			end = i+1;
			break;
		}
		return src.substring(start, end);
	}
	public static boolean checkAlphaAndNumber(String target, char[] specials) {
		if (target == null || target.length() == 0) {
			return false;
		}

		for (int i = 0; i < target.length(); i++) {
			char ch = target.charAt(i);
			if (ch >= '0' && ch <= '9' // 数字
					|| ch >='a' && ch <= 'z' // 小写字母
					|| ch >='A' && ch <= 'Z' // 大写字母	
					) {
				// OK
			} else {
				if (specials!= null) {
					boolean isSpacial = false;
					for (int j = 0; j < specials.length; j++) {
						if (specials[j]==ch) {
							isSpacial = true;
							break;
						}
					}
					if (!isSpacial) {
						return false;
					}
				}
			}
		}
		return true;
	}
	public static boolean checkLittleAlphaAndNumber(String target, char[] specials) {
		if (target == null || target.length() == 0) {
			return false;
		}

		for (int i = 0; i < target.length(); i++) {
			char ch = target.charAt(i);
			if (ch >= '0' && ch <= '9' // 数字
					|| ch >='a' && ch <= 'z' // 小写字母
					) {
				// OK
			} else {
				if (specials!= null) {
					boolean isSpacial = false;
					for (int j = 0; j < specials.length; j++) {
						if (specials[j]==ch) {
							isSpacial = true;
							break;
						}
					}
					if (!isSpacial) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * email格式检测
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		// String emailPattern="[a-zA-Z0-9][a-zA-Z0-9._-]{0,16}[a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+.[a-zA-Z0-9]+";
		// 修改：增加对中文名称的支持；支持3级域名
		String emailPattern="[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9._-]{0,16}[\u4E00-\u9FA5a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+){1,3}";
		boolean result = false;
		result = Pattern.matches(emailPattern, email);     
		if(result == true){
			if(email.replaceAll("[\u4E00-\u9FA5]", "aa").length()>32){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 电话/手机号码格式检测
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkPhoneNum(String phoneNum){
		String pattern = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
		boolean result = Pattern.matches(pattern, phoneNum);   
		return result;
	}

	/**
	 * POI私有字段内容检查
	 * @param value
	 * @param fieldDef
	 * @return
	 */
	public static boolean isPoiDataHasError(String value, JSONObject fieldDef, Boolean valueIsLabel) {
		if (fieldDef == null) {
			return true;
		}
		int field_type = fieldDef.getInteger("field_type");
		int field_size = fieldDef.getInteger("field_size");
		switch (field_type) {
			case VbConstant.POI_FIELD_TYPE.文本框:
				// 整数数字
				if (field_size == -1) {
					return !isInteger(value);
				}
				// 字符串
				// 检查长度
				return countChineseEn(value) > field_size;

			case VbConstant.POI_FIELD_TYPE.文本域:
				return countChineseEn(value) > field_size;

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
	 * Poi上传用文件是否存在check
	 * @param value
	 * @param field_size
	 * @return
	 */
	public static boolean fileFieldIsError(String value, String dir) {
		if (value == null || value.isEmpty()) {
			return false;
		}
		if (dir == null) {
			return true;
		}
		File file = new File(dir + value);
		return !(file.exists() && file.isFile());
	}

	/**
	 * Poi字段长度检查
	 * @param value
	 * @param field_size
	 * @return
	 */
	public static boolean checkPoiDefaultStr(String value, Integer field_size) {
		return countChineseEn(value) > field_size;
	}

	/**
	 *  Poi字段长度检查
	 * @param value
	 * @return
	 */
	public static boolean checkPoiDefaultStr(String value) {
		return checkPoiDefaultStr(value, 255);
	}

	/**
	 * 检查经度
	 * @param lngLat
	 * @return
	 */
	public static boolean checkPoiLng(String lng) {
		if (lng == null || lng.isEmpty()) {
			return false; 
		}
		try {
			BigDecimal decimal = new BigDecimal(lng).setScale(6, BigDecimal.ROUND_HALF_DOWN);
			return decimal.compareTo(MaxLng) > 0 || decimal.compareTo(MinLng) < 0;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 检查纬度
	 * @param lngLat
	 * @return
	 */
	public static boolean checkPoiLat(String lat) {
		if (lat == null || lat.isEmpty()) {
			return false; 
		}
		try {
			BigDecimal decimal = new BigDecimal(lat).setScale(6, BigDecimal.ROUND_HALF_DOWN);
			return decimal.compareTo(MaxLat) > 0 || decimal.compareTo(MinLat) < 0;
		} catch (Exception e) {
			return false;
		}
	}

	//	public static void main(String[] args) {
	//		System.out.println(checkAlphaAndNumber("audio,en_name,intro1,intro2,intro3,intro4,lat,lng,sketch,viewall,zh_name,title1,title2,title3,title4", new char[]{'_'}));
	//	}
}
