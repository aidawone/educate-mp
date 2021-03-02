package he.edu.order.service;

import he.edu.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
public interface TOrderService extends IService<TOrder> {

    String addOrder(String id, HttpServletRequest request);

    TOrder getOrderByNo(String orderNo);

    boolean getOrderByCourseIdAndUserId(String courseId, String userId);
}
