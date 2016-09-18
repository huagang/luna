package ms.luna.common;

import java.text.MessageFormat;

import ms.luna.biz.util.MsLogger;
import ms.luna.common.MsLunaResource;

/**
 * 应用内使用的消息类。
 * <p>各个层消息统一处理</p>
 * 
 * @author Mark(VB Inc.)
 *
 */
public final class MsLunaMessage {
	private MsLunaMessage() {
	}
	private static MsLunaMessage msLunaMessage = new MsLunaMessage();
	public static MsLunaMessage getInstance() {
		return msLunaMessage;
	}

	private static String getTemplate(String key){
		MsLunaResource messages = MsLunaResource.getResource(MsLunaResource.MESSAGES_RESOURCE);
		if(messages != null && messages.containsKey(key)){
			return messages.getValue(key);
		}
		return null;
	}

	/**
	 * 获取消息ID对应的消息内容
	 * @param key
	 * @param args
	 * @return 消息内容
	 */
	public String getMessage(String key, Object...args){
		String temp = MsLunaMessage.getTemplate(key);
		if (temp == null) {
			MsLogger.error("message Id["+ key+"] is not found.");
		}
		return MessageFormat.format(temp, args);
	}
}
