package he.edu.order.service;

import he.edu.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<String, Object> payOrderNo(String orderNo);

    Map<String, String> selectPayCode(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
