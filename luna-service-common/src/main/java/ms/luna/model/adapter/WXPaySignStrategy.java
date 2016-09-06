/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.model.adapter;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.util.VbMD5;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by SDLL18 on 16/9/5.
 *
 * @author SDLL18
 */
public class WXPaySignStrategy extends AbstractPaySignStrategy {

    private final static Logger logger = Logger.getLogger(WXPaySignStrategy.class);


    @Override
    public Boolean checkSign(String data) throws ParserConfigurationException, SAXException, IOException {
        String sign = getSignFromResponseString(data);
        String signFromWX = getContent(data, "sign");
        if (signFromWX.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String signMessage(String data) {
        JSONObject json = JSONObject.parseObject(data);
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (!entry.getValue().equals("")) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + PARTNER_KEY;
        //logger.info("Sign Before MD5:" + result);
        result = VbMD5.getCommonMD5Str(result).toUpperCase();
        //logger.info("Sing Result:" + result);
        return result;
    }

    private String getContent(String xml, String key) {
        int start = xml.indexOf("<" + key + ">") + 2 + key.length();
        int end = xml.indexOf("</" + key + ">");
        String content = xml.substring(start, end);
        if (content.startsWith("<![CDATA[") && content.endsWith("]]>")) {
            content = content.substring(9, content.length() - 3);
        }
        return content;
    }

    private String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
        JSONObject object = JSONObject.parseObject(getPayDataParseStrategy().getFromTransfer(responseString));
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        object.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return signMessage(object.toJSONString());
    }
}
