package he.edu.cms.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 22:01 2021/3/2
 */
@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public ResultEntity status(String courseId, String userId) {
        return ResultEntity.error().message("查询订单状态失败");
    }
}
