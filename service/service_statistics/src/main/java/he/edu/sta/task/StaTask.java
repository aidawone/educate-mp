package he.edu.sta.task;

import he.edu.sta.service.StatisticsDailyService;
import he.edu.sta.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务 生成统计数据
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:51 2021/3/3
 */
@Component
public class StaTask {


    final StatisticsDailyService statisticsDailyService;

    public StaTask(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }

    //每天凌晨一点执行
    @Scheduled(cron = "0/5 0 1 * * ?")
    private void daily() {
        //获取前一天的天数
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.statisticsEveryDay(date);
    }
}
