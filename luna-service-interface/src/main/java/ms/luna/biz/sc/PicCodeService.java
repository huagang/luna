/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by SDLL18 on 16/8/29.
 */
public interface PicCodeService {

    Boolean saveCode(String key, String code);

    JSONObject checkCode(String key, String code, Boolean isRemove);
}
