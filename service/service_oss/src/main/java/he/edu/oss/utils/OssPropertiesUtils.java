package he.edu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:22 2021/2/23
 */
@Component
public class OssPropertiesUtils implements InitializingBean {

    @Value("${alliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${alliyun.oss.file.keyId}")
    private String keyId;

    @Value("${alliyun.oss.file.keySecret}")
    private String keySecret;

    @Value("${alliyun.oss.file.bucketName}")
    private String bucketName;

    @Value("${spring.resources.static-locations}")
    private String filePath;

    @Value("${nginx.port}")
    private String nginxPort;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    public static String FILE_PATH;

    public static String NGINX_PORT;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;

        FILE_PATH = filePath.replace("file:", "");

        NGINX_PORT = nginxPort;
    }
}
