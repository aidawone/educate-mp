package he.edu.order.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:47 2021/3/2
 */
@Component
public class EduCourseClientImpl implements EduCourseClient {
    @Override
    public ResultEntity detail(String id) {
        return ResultEntity.error().message("远程获得课程信息失败！");
    }
}
