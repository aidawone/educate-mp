package he.edu.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.cms.client.OrderClient;
import he.edu.cms.entity.EduChapter;
import he.edu.cms.entity.EduCourse;
import he.edu.cms.entity.vo.EduCourseDetailVo;
import he.edu.cms.entity.vo.EduCourseVo;
import he.edu.cms.service.EduChapterService;
import he.edu.cms.service.EduCourseService;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.commonutils.utils.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/front/course")
@CrossOrigin
public class EduCourseController {

    final EduCourseService courseService;
    final EduChapterService chapterService;
    final OrderClient orderClient;

    public EduCourseController(EduCourseService courseService,
                               EduChapterService chapterService,
                               OrderClient orderClient) {
        this.chapterService = chapterService;
        this.courseService = courseService;
        this.orderClient = orderClient;
    }

    @GetMapping("/index")
    public ResultEntity index(Long page, Long size, EduCourseVo courseVo) {
        Page<EduCourse> build = new Page<>(page, size);
        Map<String, Object> map = courseService.ListByConditionsAndOrder(build, courseVo);
        return ResultEntity.ok().data(map);
    }

    @GetMapping("/detail")
    public ResultEntity detail(String id, HttpServletRequest request) {
        EduCourseDetailVo vo = courseService.getCourseDetailById(id);
        //查询出课程的章节和小节
        List<EduChapter> eduChapters = chapterService.treeChapterVideoById(id);
        //查询出是否支付订单
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBug = false;
        if (!StringUtils.isEmpty(userId)) {
            ResultEntity status = orderClient.status(id, userId);
            Map<String, Object> data = status.getData();
            isBug = (Boolean) data.get("isBug");
        }

        return ResultEntity
                .ok()
                .data("chapters", eduChapters)
                .data("course", vo)
                .data("isBug", isBug);
    }
}

