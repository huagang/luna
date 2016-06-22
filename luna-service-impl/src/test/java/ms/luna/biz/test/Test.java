package ms.luna.biz.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;
import java.util.Map;

public class Test {

	static class Bean {
		@JSONField(name="testDate", format="yyyy-MM-dd HH:mm:ss")
		private Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
	public static void main(String[] args) {

		Bean bean = new Bean();
		bean.setDate(new Date());

		System.out.println(JSON.toJSONString(bean, SerializerFeature.WriteDateUseDateFormat));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "chenshangan");
		jsonObject.put("age", 30);
		Map<String, Object> map = jsonObject.toJavaObject(Map.class);
		System.out.println(map);
	}

}
