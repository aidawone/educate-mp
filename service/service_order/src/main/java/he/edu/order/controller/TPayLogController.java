package he.edu.order.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.order.service.TPayLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/order/pay")
public class TPayLogController {

    final TPayLogService tPayLogService;

    public TPayLogController(TPayLogService tPayLogService) {
        this.tPayLogService = tPayLogService;
    }

    //生成支付二维码
    @GetMapping("/qrcode")
    public ResultEntity qrcode(String orderNo) {
        Map<String, Object> map = tPayLogService.payOrderNo(orderNo);
        return ResultEntity.ok().data(map);
    }

    //根据订单查询支付状态
    @GetMapping("/code")
    public ResultEntity code(String orderNo) {
        Map<String, String> map = tPayLogService.selectPayCode(orderNo);
        System.out.println("--------------------" + map);
        if (map == null) {
            return ResultEntity.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            tPayLogService.updateOrdersStatus(map);
            return ResultEntity.ok().message("支付成功");
        }
        return ResultEntity.ok().code(25000).message("支付中");
    }
}

