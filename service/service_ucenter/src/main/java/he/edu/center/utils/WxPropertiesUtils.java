package he.edu.center.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:22 2021/3/1
 */
@Component
public class WxPropertiesUtils implements InitializingBean {

    @Value("${wx.open.appid}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
        REDIRECT_URL = this.redirectUrl;
    }
}
