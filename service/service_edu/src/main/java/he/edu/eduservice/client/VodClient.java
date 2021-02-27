package he.edu.eduservice.client;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:54 2021/2/27
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDrageClient.class)
public interface VodClient {
    @DeleteMapping("/vod/video/delete")
    public ResultEntity delete(@RequestParam("id") String id);
}
