package he.edu.order.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:31 2021/3/2
 */

@Component
public class EduUcenterClientImpl implements EduUcenterClient {

    @Override
    public ResultEntity detail(String id) {
        return ResultEntity.error().message("远程调用失败!");
    }
}
