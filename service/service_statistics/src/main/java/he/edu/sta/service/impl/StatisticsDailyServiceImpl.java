package he.edu.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.sta.client.UcenterClient;
import he.edu.sta.entity.StatisticsDaily;
import he.edu.sta.mapper.StatisticsDailyMapper;
import he.edu.sta.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    final UcenterClient ucenterClient;

    public StatisticsDailyServiceImpl(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @Override
    public void statisticsEveryDay(String day) {
        //第一步，远程调用人员注册数
        ResultEntity entity = ucenterClient.loginNum(day);
        if (!entity.getSuccess()) {
            throw new HeException(20001, "远程调用失败");
        }

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        boolean remove = this.remove(wrapper);
        if (!remove) {
            throw new HeException(20001, "删除数据库失败！");
        }

        //首先，将同一个日期删除
        Map<String, Object> data = entity.getData();
        Integer loginNum = (Integer) data.get("loginNum");

        StatisticsDaily daily = new StatisticsDaily();
        daily.setLoginNum(loginNum);
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        daily.setDateCalculated(day);
        daily.setRegisterNum(RandomUtils.nextInt(100, 200));
        daily.setVideoViewNum(RandomUtils.nextInt(100, 200));

        boolean save = this.save(daily);
        if (!save) {
            throw new HeException(20001, "插入数据库失败！");
        }
    }

    /**
     * 返回json数据，生成折现图
     *
     * @param type  统计字段
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    @Override
    public Map<String, Object> getChartByTypeAndTime(String type, String begin, String end) {
        if (StringUtils.isEmpty(type)) {
            throw new HeException(20001, "生成折线图的类型不能为空！");
        }
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type)
                .between("date_calculated", begin, end)
                .orderByDesc("date_calculated");

        List<StatisticsDaily> data = this.list(wrapper);

        if (data.size() <= 0) {
            throw new HeException(20001, "查无此数据");
        }
        Map<String, Object> map = new HashMap<>();
        List<String> dayList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            StatisticsDaily daily = data.get(i);
            dayList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    throw new HeException(20001, "type类型错误");
            }
        }
        map.put("days", dayList);
        map.put("nums", numList);
        return map;
    }
}
