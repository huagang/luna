/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.schedule.service;

import com.alibaba.fastjson.JSONObject;
import ms.luna.model.WXPaymethodEnum;
import ms.luna.model.adapter.PayInfoProcessAdapter;

import java.util.Map;

/**
 * Created by SDLL18 on 16/9/1.
 */
public abstract class WXPayScheduler {


    private Map<WXPaymethodEnum,PayInfoProcessAdapter>  adapterMap;

    private WXPayScheduler(){
        //TODO
    }

    public abstract String sendPayRequest(JSONObject data, WXPaymethodEnum method);

    public abstract String sendPost();

    public abstract JSONObject parse(String xml);

    public abstract boolean validate(String xml);

}
