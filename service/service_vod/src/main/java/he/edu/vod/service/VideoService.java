package he.edu.vod.service;

import java.io.InputStream;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 20:11 2021/2/26
 */
public interface VideoService {
    String uploadInput(String title, String fileName, InputStream inputStream);

    void deleteByIds(String ids);

    String getPlayAuth(String id);
}
