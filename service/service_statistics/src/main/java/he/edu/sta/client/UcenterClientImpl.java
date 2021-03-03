package he.edu.sta.client;

import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 10:40 2021/3/3
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public ResultEntity loginNum(String day) {
        throw new HeException(20001, "远程调用：统计每日登录人数失败！");
    }
}
