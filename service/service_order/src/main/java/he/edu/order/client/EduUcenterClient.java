package he.edu.order.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:31 2021/3/2
 */

@Component
@FeignClient(name = "service-ucenter", fallback = EduUcenterClientImpl.class)
public interface EduUcenterClient {
    @GetMapping("/api/member/detail")
    public ResultEntity detail(@RequestParam("id") String id);
}
