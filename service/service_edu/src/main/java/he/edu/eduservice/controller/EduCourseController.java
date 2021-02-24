package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.vo.EduCourseVo;
import he.edu.eduservice.service.EduCourseService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/back/course")
@CrossOrigin
public class EduCourseController {

    final EduCourseService service;

    public EduCourseController(EduCourseService service) {
        this.service = service;

    }

    @PostMapping("/add")
    public ResultEntity add(@RequestBody EduCourseVo vo) {
        String id = service.saveCourseDesc(vo);
        return ResultEntity.ok().data("id", id);
    }

}

