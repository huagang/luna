package ms.luna.dubbo.provider;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.MsZoneCacheBL;
import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.util.COSUtil;

public class LunaProviderDev {
	
	private final static Logger logger = Logger.getLogger(LunaProviderDev.class);

	public static void main(String[] args) {
		System.out.println("LunaProvider Service is starting......");
		final LunaStarter lunaStarter = new LunaStarter("dubbo_provider_dev.xml");
		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
			public void run() {
				lunaStarter.shutdown();
			}
		});
		lunaStarter.startup();
	}

}