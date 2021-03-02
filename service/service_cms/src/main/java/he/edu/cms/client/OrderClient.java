package he.edu.cms.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 21:59 2021/3/2
 */
@Component
@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/order/order/status")
    public ResultEntity status(@RequestParam("courseId") String courseId, @RequestParam("userId") String userId);
}
