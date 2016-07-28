package ms.luna.biz.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MtaWrapper {
	private final static Logger logger = Logger.getLogger(MtaWrapper.class);

	private static final String MS_SECRET_KEY ="yi)CKE^-j2q(D3+H";
	private static final String REGISTER_URL = "http://mta.qq.com/h5/api/ctr_register/register";
	private static final String DELETE_URL = "http://mta.qq.com/h5/api/ctr_register/delete_app";
	private static final String SOURCE = "4";

	/**
	 *
	 * @param paramMap sorted map
	 * @return
	 */
	private static String generateSign(Map<String, Object> paramMap, String appKey) {
		StringBuilder sb = new StringBuilder(appKey);
		for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}

		return VbMD5.getCommonMD5Str(sb.toString());

	}

	public static String createApp(String appName, int appType, String domain, long adminQQ) {

		Map<String, Object> requestParamMap = new TreeMap<>();
		requestParamMap.put("app_name", appName);
		requestParamMap.put("app_type", appType);
		requestParamMap.put("source", SOURCE);

		if(StringUtils.isNotBlank(domain)) {
			try {
				requestParamMap.put("domain", URLEncoder.encode(domain, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Invalid encoding for domain: " + domain, e);
			}
		}
		requestParamMap.put("admin_qq", adminQQ);
		requestParamMap.put("sign", generateSign(requestParamMap, MS_SECRET_KEY));
		StringBuilder sb = new StringBuilder(REGISTER_URL + "?");
		for(Map.Entry<String, Object> entry : requestParamMap.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		String url = sb.substring(0, sb.length() - 1);

		return httpGet(url);

	}

	public static String deleteApp(int appId, String appKey) {

		Map<String, Object> requestParamMap = new TreeMap<>();
		requestParamMap.put("app_id", appId);
		requestParamMap.put("source", SOURCE);
		requestParamMap.put("sign", generateSign(requestParamMap, appKey));
		StringBuilder sb = new StringBuilder(DELETE_URL + "?");
		for(Map.Entry<String, Object> entry : requestParamMap.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		String url = sb.substring(0, sb.length() - 1);

		return httpGet(url);
	}

	private static String httpGet(String url) {

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		HttpMethodParams params = new HttpMethodParams();
		params.setSoTimeout(500);
		getMethod.setParams(params);
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode == HttpStatus.SC_OK) {
				return new String(getMethod.getResponseBody(), "utf-8");
			}
		} catch (IOException ex) {
			logger.error("Failed to request url: " + url, ex);
		}

		return null;
	}

	@Test
	public void test() throws Exception {

		String ret = null;
		ret = MtaWrapper.createApp("new_app_test",2, "luna.visualbusiness.com", 3463673430l);
        System.out.println("result: " + ret);

//		ret = MtaWrapper.deleteApp(500145273, "4a1c6998022f828856464e6f6d94d754");
//		System.out.println("result: " + ret);
    }
}
