package he.edu.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import he.edu.commonutils.entity.HeException;
import he.edu.order.entity.TOrder;
import he.edu.order.entity.TPayLog;
import he.edu.order.mapper.TPayLogMapper;
import he.edu.order.service.TOrderService;
import he.edu.order.service.TPayLogService;
import he.edu.order.utils.ConstantUtils;
import he.edu.order.utils.HttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    final TOrderService tOrderService;

    public TPayLogServiceImpl(TOrderService tOrderService) {
        this.tOrderService = tOrderService;
    }


    @Override
    public Map<String, Object> payOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            throw new HeException(20001, "参数不能为空");
        }
        //根据订单号查询
        TOrder order = tOrderService.getOrderByNo(orderNo);

        //2 使用map设置生成二维码需要参数
        Map m = new HashMap();
        m.put("appid", ConstantUtils.APP_ID);
        m.put("mch_id", ConstantUtils.PARTNER);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle()); //课程标题
        m.put("out_trade_no", orderNo); //订单号
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", ConstantUtils.NOTIFY_URL);

        m.put("trade_type", "NATIVE");
        //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        //设置xml格式的参数
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m, ConstantUtils.PARTNER_KEY));
        } catch (Exception e) {
            throw new HeException(20001, "参数转成xml失败！");
        }
        client.setHttps(true);
        //执行post请求发送
        try {
            client.post();
        } catch (IOException e) {
            throw new HeException(20001, "生成微信支付二维码失败！");
        }
        //4 得到发送请求返回结果
        //返回内容，是使用xml格式返回
        String xml = null;
        try {
            xml = client.getContent();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new HeException(20001, "响应体获取失败！！");
        }

        //把xml格式转换map集合，把map集合返回
        Map<String, String> resultMap = null;
        try {
            resultMap = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            throw new HeException(20001, "xml转成map失败！");
        }
        //最终返回数据 的封装
        Map map = new HashMap();
        map.put("out_trade_no", orderNo);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
        map.put("code_url", resultMap.get("code_url"));        //二维码地址
        return map;
    }

    @Override
    public Map<String, String> selectPayCode(String orderNo) {
        //1、封装参数
        Map<String, String> m = null;
        HttpClient client = null;
        Map<String, String> resultMap = null;
        try {
            m = new HashMap<>();
            m.put("appid", ConstantUtils.APP_ID);
            m.put("mch_id", ConstantUtils.PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            try {
                client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
                client.setXmlParam(WXPayUtil.generateSignedXml(m, ConstantUtils.PARTNER_KEY));
                client.setHttps(true);
                client.post();
            } catch (Exception e) {
                throw new HeException(20001, "map和xml格式转换失败！！");
            }
            //3 得到请求返回内容
            String xml = client.getContent();
            resultMap = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            throw new HeException(20001, "查询微信订单失败！");
        }
        return resultMap;
    }

    //根据返回的订单号修改状态
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = tOrderService.getOrderByNo(orderNo);
        if (StringUtils.isEmpty(order)) {
            throw new HeException(20001, "数据库中不存在该对象");
        }
        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);
        boolean flag = tOrderService.updateById(order);
        if (!flag) {
            throw new HeException(20001, "更新失败");
        }
    }

}