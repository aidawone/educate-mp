package he.edu.sta.service;

import he.edu.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-03-03
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void statisticsEveryDay(String day);
    /**
     * 返回json数据，生成折现图
     *
     * @param type  统计字段
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    Map<String, Object> getChartByTypeAndTime(String type, String begin, String end);
}
