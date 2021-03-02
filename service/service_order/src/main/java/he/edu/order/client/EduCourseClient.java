package he.edu.order.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:32 2021/3/2
 */
@Component
@FeignClient(name = "service-edu", fallback = EduCourseClientImpl.class)
public interface EduCourseClient {
    @GetMapping("/back/course/detail")
    public ResultEntity detail(@RequestParam("id") String id);
}
