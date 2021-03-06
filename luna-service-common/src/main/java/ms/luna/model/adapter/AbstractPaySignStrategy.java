package ms.luna.model.adapter;


/**
 * 本抽象类中实现策略依赖关系
 *
 * Created by SDLL18 on 16/9/5.
 *
 * @author SDLL18
 */
public abstract class AbstractPaySignStrategy implements PaySignStrategy {

    public static String PARTNER_KEY = "1c3O0T3v3s2k0o2S33171s3K2e1I2i0u";//商户号对应的密钥(微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置)

    private PayDataParseStrategy payDataParseStrategy;

    public AbstractPaySignStrategy(){
        payDataParseStrategy = new WXPayDataParseStrategy();
    }

    public AbstractPaySignStrategy(PayDataParseStrategy strategy) {
        if (strategy != null) {
            payDataParseStrategy = strategy;
        } else {
            payDataParseStrategy = new WXPayDataParseStrategy();
        }
    }

    public void setPayDataParseStrategy(PayDataParseStrategy payDataParseStrategy) {
        this.payDataParseStrategy = payDataParseStrategy;
    }

    public PayDataParseStrategy getPayDataParseStrategy() {
        return payDataParseStrategy;
    }

}
