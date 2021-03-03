package he.edu.cms.controller;

import he.edu.cms.service.CrmBannerService;
import he.edu.cms.service.EduCourseService;
import he.edu.cms.service.EduTeacherService;
import he.edu.commonutils.entity.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 前台首页控制器
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 17:21 2021/2/27
 */
@RestController
@RequestMapping("/front/cms")
public class IndexFrontController {

    private final static Logger LOGGER = LoggerFactory.getLogger(IndexFrontController.class);

    final CrmBannerService bannerService;
    final EduTeacherService teacherService;
    final EduCourseService courseService;

    final ThreadPoolTaskExecutor taskExecutor;

    public IndexFrontController(CrmBannerService bannerService,
                                EduTeacherService teacherService,
                                EduCourseService courseService,
                                ThreadPoolTaskExecutor taskExecutor) {
        this.bannerService = bannerService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.taskExecutor = taskExecutor;
    }

    //前台首页数据
    @Cacheable(value = "index",key = "'info'")
    @GetMapping("/index")
    public ResultEntity index() {
        //首先查询轮播图
        final CountDownLatch latch = new CountDownLatch(3);
        final List<?>[] list = new List[(int) latch.getCount()];

        taskExecutor.execute(() -> {
            try {
                list[0] = bannerService.getListByIndex();
            } catch (Exception e) {
                LOGGER.error(e.toString());
            } finally {
                //线程数-1
                latch.countDown();
            }
        });
        taskExecutor.execute(() -> {
            try {
                //查询8条热门课程
                list[1] = courseService.getCourseHot();
            } catch (Exception e) {
                LOGGER.error(e.toString());
            } finally {
                //线程数-1
                latch.countDown();
            }
        });
        taskExecutor.execute(() -> {
            try {
                //查询前4名教师
                list[2] = teacherService.getTeacherHot();
            } catch (Exception e) {
                LOGGER.error(e.toString());
            } finally {
                //线程数-1
                latch.countDown();
            }
        });
        //等子线程执行完
        try {
            latch.await();
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        return ResultEntity
                .ok()
                .data("list", list);
    }
}
