package he.edu.sta.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.sta.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-03-03
 */
@RestController
@RequestMapping("/sta/statistics")
public class StatisticsDailyController {

    final StatisticsDailyService statisticsDailyService;

    public StatisticsDailyController(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }

    @GetMapping("/day")
    public ResultEntity statistics(String day) {
        statisticsDailyService.statisticsEveryDay(day);
        return ResultEntity.ok();
    }

    /**
     * 返回json数据，生成折现图
     *
     * @param type  统计字段
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    @GetMapping("/chart")
    public ResultEntity getChart(String type, String begin, String end) {
        Map<String, Object> map = statisticsDailyService.getChartByTypeAndTime(type, begin, end);
        return ResultEntity.ok().data(map);
    }
}

