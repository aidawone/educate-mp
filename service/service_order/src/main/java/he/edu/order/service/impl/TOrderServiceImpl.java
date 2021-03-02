package he.edu.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.commonutils.utils.JwtUtils;
import he.edu.order.client.EduCourseClient;
import he.edu.order.client.EduUcenterClient;
import he.edu.order.entity.TOrder;
import he.edu.order.mapper.TOrderMapper;
import he.edu.order.service.TOrderService;
import he.edu.order.utils.OrderNoUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-02
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    final EduUcenterClient eduUcenterClient;
    final EduCourseClient eduCourseClient;

    public TOrderServiceImpl(EduUcenterClient eduUcenterClient,
                             EduCourseClient eduCourseClient) {

        this.eduUcenterClient = eduUcenterClient;
        this.eduCourseClient = eduCourseClient;
    }


    @Override
    public String addOrder(String id, HttpServletRequest request) {
        if (StringUtils.isEmpty(id)) {
            throw new HeException(20001, "参数不能为空！");
        }
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        ResultEntity detail = eduUcenterClient.detail(userId);
        //拿到用户数据
        Map<String, Object> data = detail.getData();
        Map<String, Object> user = (Map<String, Object>) data.get("user");

        //拿到课程数据
        ResultEntity courseResult = eduCourseClient.detail(id);
        Map<String, Object> resultData = courseResult.getData();
        Map<String, Object> items = (Map<String, Object>) resultData.get("items");
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(id);
        order.setCourseTitle(items.get("title").toString());
        order.setCourseCover(items.get("cover").toString());
        order.setTeacherName(items.get("teacherName").toString());
        order.setTotalFee((BigDecimal) items.get("price"));
        order.setMemberId(user.get("id").toString());
        order.setMobile(user.get("mobile").toString());
        order.setNickname(user.get("nickname").toString());
        order.setPayType(1);
        order.setStatus(0);
        boolean save = this.save(order);
        if (!save) {
            throw new HeException(20001, "插入数据库失败!");
        }
        return order.getOrderNo();
    }

    @Override
    public TOrder getOrderByNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            throw new HeException(20001, "参数不能为null");
        }
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder one = this.getOne(wrapper);
        if (StringUtils.isEmpty(one)) {
            throw new HeException(20001, "数据库中不存在该对象");
        }
        return one;
    }

    @Override
    public boolean getOrderByCourseIdAndUserId(String courseId, String userId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId)
                .eq("member_id", userId)
                .eq("is_deleted", 0)
                .eq("status", 1);
        int count = this.count(wrapper);
        if (count > 1) {
            return true;
        }
        return false;
    }
}
