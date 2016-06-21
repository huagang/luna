package ms.luna.common;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import ms.luna.biz.util.MsLogger;

/**
 * 
 * @author Mark
 */
public final class MsLunaResource {
	private static final String RESOURCE_NAME_TARGET = "app_resources";
	
	public static final String MESSAGES_RESOURCE = "messages";
	
	private static Map<String, MsLunaResource> resources;

	private ResourceBundle resourceBundle;

	private String name;

	private MsLunaResource(String name, ResourceBundle resourceBundle) {
		this.name = name;
		this.resourceBundle = resourceBundle;
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	public static MsLunaResource getResource(String resource){
		if(resources == null){
			load();
		}
		return resources.get(resource);
	}

	/**
	 * 
	 */
	public static void load() {
		MsLogger.debug("initialise "+RESOURCE_NAME_TARGET+" start");
		resources = new HashMap<String, MsLunaResource>();
		ResourceBundle targets = getBundle(RESOURCE_NAME_TARGET);

		if (targets != null) {
			for(String name : targets.keySet()){
				MsLogger.debug("additional resource ["+ name +"] initialize");
				ResourceBundle rb = getBundle(name);
				if (rb != null) {
					MsLunaResource ar = new MsLunaResource(name,rb);
					resources.put(name, ar);
				}
			}
		}
		MsLogger.debug("initialize "+RESOURCE_NAME_TARGET+" end");
	}

	private static ResourceBundle getBundle(String name) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle(name);
			return rb;
		} catch (MissingResourceException e) {
			MsLogger.warn("cannot find resource "+name+".properties");
			return null;
		}
	}

	/**
	 * 获取键值
	 */
	public String getValue(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			MsLogger.warn("missing resource name:{"+name+"} key:{"+key+"}");
			return null;
		}
	}

	/**
	 * 返回是否存在Key
	 * @param key 
	 * @return 是否存在
	 */
	public boolean containsKey(String key) {
		return resourceBundle.containsKey(key);
	}

	/**
	 * 返回全部的key
	 * @return key的Set
	 */
	public Set<String> keySet() {
		return resourceBundle.keySet();
	}

	/**
	 * key是否为空
	 * @return 是否为空
	 */
	public boolean isEmpty() {
		return resourceBundle.keySet().isEmpty();
	}
}
