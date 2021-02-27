package he.edu.eduservice.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:10 2021/2/27
 */
@Component
public class VodFileDrageClient implements VodClient {
    @Override
    public ResultEntity delete(String id) {
        return ResultEntity.error().message("删除视频失败...");
    }
}
