package he.edu.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:22 2021/2/23
 */
@Component
public class MsmPropertiesUtils implements InitializingBean {


    @Value("${alliyun.keyId}")
    private String keyId;

    @Value("${alliyun.keySecret}")
    private String keySecret;

    @Value("${alliyun.signName}")
    private String signName;

    @Value("${alliyun.templateCode}")
    private String templateCode;

    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
    }
}
