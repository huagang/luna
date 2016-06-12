package ms.luna.dubbo.provider;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.util.COSUtil;

public class LunaProviderDev extends Thread {
	
	private final static Logger logger = Logger.getLogger(LunaProviderDev.class);

	public static void main(String[] args) {
		System.out.println("LunaProvider Service is starting......");
		LunaProviderDev provider = new LunaProviderDev();
		provider.start();
	}
	@Override
	public void run() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"dubbo_provider_dev.xml"});
		context.start();
		Resource resource = context.getResource("classpath:config.properties");
		Properties properties = new Properties();
		try {
			properties.load(resource.getInputStream());
			ServiceConfig.setConfig(properties);
			COSUtil.cosBaseDir = ServiceConfig.getString(ServiceConfig.COS_BASE_DIR);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Failed to load config");;
		}
		MsZoneCacheBL msZoneCacheBL = (MsZoneCacheBL)context.getBean("msZoneCacheBL");
		msZoneCacheBL.init();
		System.out.println("LunaProvider Service is started!");
		try {
			while(true) {
				Thread.sleep(1000*60*60*24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}