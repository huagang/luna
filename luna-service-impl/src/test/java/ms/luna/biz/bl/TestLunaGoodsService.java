package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.sc.FarmPageService;
import ms.luna.biz.sc.LunaGoodsService;
import ms.luna.biz.util.COSUtil;
import ms.luna.biz.util.MsLogger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created: by greek on 16/8/30.
 */
public class TestLunaGoodsService {

    private LunaGoodsService lunaGoodsService;

    @Before
    public void setUp() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"dubbo_provider_dev.xml"});
        context.start();
        Resource resource = context.getResource("classpath:config.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
            ServiceConfig.setConfig(properties);
        } catch (IOException e1) {
            MsLogger.error("Failed to load config");
        }

        lunaGoodsService = (LunaGoodsService)context.getBean("lunaGoodsService");
    }

    @Test
    public void getGoodsCategories(){
        String keyword = "banana";
        JSONObject result = lunaGoodsService.getGoodsCategories(keyword);
        System.out.println("---------------------------------");
        System.out.println(result.toString());
    }
}
