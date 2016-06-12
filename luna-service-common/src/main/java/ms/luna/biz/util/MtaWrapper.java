package ms.luna.biz.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class MtaWrapper {
	private static final String MS_SECRET_KEY ="PQ0&(2mAs5Ns1!@M";
	private static final String URL_REGIST = "http://mta.qq.com/h5/api/ctr_register/register";
	private static final String SOURCE = "4";

	private static String getSingedParams(String params) {

		Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});

		String[] tokens = params.split("&");
		for (String t : tokens) {
			String[] kv = t.split("=", t.length());
			if (kv.length == 2) {
				sortedMap.put(kv[0], t);
			}
		}
		StringBuilder sb = new StringBuilder(MS_SECRET_KEY);
		for (String value: sortedMap.values()) {
			sb.append(value);
		}

		/*
		 * 【原始参数值】 + 【签名】 + 【秘钥】+【升序后的参数的MD5值】
		 */
		return params + "&sign=" + VbMD5.getCommonMD5Str(sb.toString());
	}
	/**
	 * source 类型默认为4，同样参数多次调用会返回不同的 app_id 和 app_secert_key
	 * 
	 * @param app_name		应用名称，不做重复检查
	 * @param app_type		应用类型
	 * @param domain		站点域名，可以为空，传入""即可
	 * @param admin_qq		管理员QQ号码
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static String getRegistResult(String app_name,int app_type,String domain, long admin_qq) throws IOException, Exception{

		StringBuilder sortedParams = new StringBuilder();
		try {
			sortedParams.append("admin_qq=")
			.append(admin_qq)
			.append("&app_name=")
			.append(URLEncoder.encode(app_name,"UTF-8"))
			.append("&app_type=")
			.append(app_type);
			if(domain != null && !domain.isEmpty()){
				sortedParams.append("&domain=")
				.append(URLEncoder.encode(domain,"UTF-8"));
			}
			sortedParams.append("&source=")
			.append(SOURCE);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("不支持UTF-8编码类型");
		}
		String uri = MtaWrapper.URL_REGIST + "?" +getSingedParams(sortedParams.toString());

		URLConnection conn = MsHttpRequest.sendPost(uri);
		return MsHttpRequest.conver2JsonString(conn.getInputStream());
	}

	@Test
	public void test() {
        String sr = null;
		try {//regionNmZh, 微景展通用类型, H5轻应用的域名domain, 腾讯云管理用QQ号码
			sr = MtaWrapper.getRegistResult("testfirst",2,"", 277239523L);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("str:"+sr);
    }
}
