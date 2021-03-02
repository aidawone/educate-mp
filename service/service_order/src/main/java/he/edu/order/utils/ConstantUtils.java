package he.edu.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 19:39 2021/3/2
 */
@Component
public class ConstantUtils implements InitializingBean {

    @Value("${weixin.pay.appId}")
    private String appId;
    @Value("${weixin.pay.partner}")
    private String partner;
    @Value("${weixin.pay.partnerKey}")
    private String partnerKey;
    @Value("${weixin.pay.notifyUrl}")
    private String notifyUrl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;


    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        PARTNER = this.partner;
        PARTNER_KEY = this.partnerKey;
        NOTIFY_URL = this.notifyUrl;
    }
}
