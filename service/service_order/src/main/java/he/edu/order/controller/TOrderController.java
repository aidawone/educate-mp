package he.edu.order.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.order.entity.TOrder;
import he.edu.order.service.TOrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/order/order")
@CrossOrigin
public class TOrderController {

    final TOrderService service;

    public TOrderController(TOrderService service) {
        this.service = service;
    }

    //生成订单
    @GetMapping("/add")
    public ResultEntity add(String id, HttpServletRequest request) {
        //首先远程调用获得用户信息
        String orderNo = service.addOrder(id, request);
        //远程调用获得课程信息
        return ResultEntity.ok().data("id", orderNo);
    }

    //根据订单号获得信息
    @GetMapping("/detail")
    public ResultEntity detail(String orderNo) {
        TOrder order = service.getOrderByNo(orderNo);
        return ResultEntity.ok().data("item", order);
    }

    @GetMapping("/status")
    public ResultEntity status(String courseId, String userId) {
        boolean flag = service.getOrderByCourseIdAndUserId(courseId, userId);
        return ResultEntity.ok().data("isBug", flag);
    }


}

