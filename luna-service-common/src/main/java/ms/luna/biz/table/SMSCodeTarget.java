/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ms.luna.biz.table;

/**
 * Created by SDLL18 on 16/9/1.
 */
public enum SMSCodeTarget {


    TRADE_APPLICATION("tradeApplication");

    private String value;


    private SMSCodeTarget(String value){
        this.value = value;
    }


    @Override
    public String toString() {
        return "SMSCodeTarget{" +
                "value='" + value + '\'' +
                '}';
    }
}
