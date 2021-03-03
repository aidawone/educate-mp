package he.edu.sta.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 10:39 2021/3/3
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //统计每天创建的人数
    @GetMapping("/api/member/loginNum")
    public ResultEntity loginNum(@RequestParam("day") String day);
}
