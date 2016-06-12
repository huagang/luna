package ms.luna.biz.util;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityUtil {
	
	private static VelocityUtil util = new VelocityUtil();
	
	private static VelocityEngine velocityEngine;
	
	private VelocityUtil() {
		
		 velocityEngine = new VelocityEngine();
		 velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 velocityEngine.init();		 
	}
	
	public static VelocityEngine getEngine() {
		return velocityEngine;
	}
	
	public static Template getTemplate(String name, String encoding) {
		return velocityEngine.getTemplate(name, encoding);
	}
	
	public static Template getTemplate(String name) {
		return getTemplate(name, "UTF-8");
	}
	
	public static void main(String[] args) {
		
		Template template = VelocityUtil.getTemplate("test.vm");
		VelocityContext context = new VelocityContext();
		List<String> items = Arrays.asList("hello1", "hello2");
		context.put("list1", items);
		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		System.out.println(sw.toString());
		
		items = Arrays.asList("world1", "world2");
		context = new VelocityContext();
		context.put("list2", items);
		sw = new StringWriter();
		template.merge(context, sw);
		System.out.println(sw.toString());
		
	}

}
