package ms.luna.web.distr.session;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ms.luna.biz.model.MsUser;
import ms.luna.biz.util.MsLogger;
import com.alibaba.fastjson.JSONObject;

public class VbDistrSessionUtil extends Thread {

	private Map<String, JSONObject> redisSession = new ConcurrentHashMap<String, JSONObject>();

	private Map<String, MsUser> loginSession = new ConcurrentHashMap<String, MsUser>();

	public static long timeout = 1000 * 60 * 3;
	public static long sleeptime = 1000 * 60 * 60 * 6;
	public MsUser getToken(String key) {
		if (key == null) {
			return null;
		}
		// 移除token
		MsUser msUser = loginSession.remove(key);
		if (msUser == null || System.currentTimeMillis() - msUser.getLoginTime().getTime() > timeout) {
			return null;
		}
		return msUser;
	}
	
	private static VbDistrSessionUtil instance = new VbDistrSessionUtil();
	private VbDistrSessionUtil() {
	}
	public static VbDistrSessionUtil getInstance() {
		return instance;
	}

	public JSONObject get(String key) {
		if (key == null) {
			return null;
		}
		// 移除token
		return redisSession.remove(key);
	}
	public void put(String key, JSONObject value) {
		redisSession.put(key, value);
	}

	public void put(String key, MsUser msUser) {
		msUser.setLoginTime(new Date());
		loginSession.put(key, msUser);
	}

	public void put(String key, String value) {
		JSONObject jsonV = JSONObject.parseObject("{}");
		jsonV.put("regist_time", System.currentTimeMillis());
		jsonV.put("value", value);
		redisSession.put(key, jsonV);
	}
	public void put(String key) {
		JSONObject jsonV = JSONObject.parseObject("{}");
		jsonV.put("regist_time", System.currentTimeMillis());
		jsonV.put("value", key);
		redisSession.put(key, jsonV);
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		try {
			while(true) {
				Thread.sleep(sleeptime);
				MsLogger.debug("session 轮询开始");
				Set<Entry<String, MsUser>> set = loginSession.entrySet();
				for (Entry<String, MsUser> entry : set) {
					MsUser msUser = entry.getValue();
					if (System.currentTimeMillis() - msUser.getLoginTime().getTime() > timeout) {
						loginSession.remove(entry.getKey());
						MsLogger.debug("删除 key["+entry.getKey()+"]");
					}
				}
			}
		} catch (Exception e) {
			MsLogger.debug(e);
		}
	}
}
