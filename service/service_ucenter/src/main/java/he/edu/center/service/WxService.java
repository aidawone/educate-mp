package he.edu.center.service;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:37 2021/3/1
 */
public interface WxService {
    String wxToQrCodeUrl();

    String wxCodeCallback(String code, String state);
}
