package ms.biz.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import ms.luna.biz.util.MsLogger;

/**
 * POI对外接口字段名称和数据库名称之间的映射
 * 
 * @author Greek
 *
 */
public class PoiApiNameMap {

	private static Map<String, String> outer2inner_nm = new LinkedHashMap<>();// 外部接口字段  与  内部数据库字段 的映射

	private static Map<String, String> inner2outer_nm = new LinkedHashMap<>();// 内部数据库字段  与  外部接口字段 的映射
	
	private static String[] apiFields = null;// 所有接口字段名称
	
	private static String[] poiFields = null;// 所有接口字段对应的数据库字段名称

	public PoiApiNameMap(String filePath) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			MsLogger.error(e);
			throw new RuntimeException(e);
		}

		Set<Entry<Object, Object>> entrys = prop.entrySet();
		String[] outerfields = new String[entrys.size()];
		String[] innerfields = new String[entrys.size()];
		int i = 0;
		for(Entry<Object, Object> entry : entrys){
			String outer = (String) entry.getKey();		//外部接口中的字段名称
			String inner = (String) entry.getValue();	//内部数据库中的字段名称
			outer2inner_nm.put(outer, inner);
			inner2outer_nm.put(inner, outer);
			outerfields[i] = outer;
			innerfields[i] = inner;
			i++;
		}
		apiFields = outerfields;
		poiFields = innerfields;
	}
	
	public String getInnerVal(String key){
		return outer2inner_nm.get(key);
	}
	
	public String getOuterVal(String key){
		return inner2outer_nm.get(key);
	}
	
	public String[] getApiFields(){
		return apiFields;
	}
	
	public String[] getPoiFields(){
		return poiFields;
	}
	
}
