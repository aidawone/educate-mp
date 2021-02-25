package he.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduCourse;
import he.edu.eduservice.entity.vo.CoursePuishVo;
import he.edu.eduservice.entity.vo.CourseVo;
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

    //根据id插叙课程和课程信息
    @GetMapping("/detail")
    public ResultEntity detail(String id) {
        EduCourseVo courseVo = service.getCourseInfoById(id);
        return ResultEntity.ok().data("items", courseVo);
    }

    //根据id修改课程和课程信息
    @PostMapping("/update")
    public ResultEntity update(@RequestBody EduCourseVo vo) {
        String returnId = service.updateCourse(vo);
        return ResultEntity.ok().data("id", returnId);
    }

    //最终发布页面展示数据
    @GetMapping("/info")
    public ResultEntity info(String id) {
        CoursePuishVo puishVo = service.getCoursePublish(id);
        return ResultEntity.ok().data("items", puishVo);
    }

    //最终发布课程操作
    @PutMapping("/publish")
    public ResultEntity publish(String id) {
        String returnId = service.publishById(id);
        return ResultEntity.ok().data("id", returnId);
    }

    @GetMapping("/index")
    public ResultEntity index(CourseVo courseVo) {
        Page<EduCourse> build = new Page<>(courseVo.getPage(), courseVo.getSize());
        Page<EduCourse> entity = service.entityByConditions(build,courseVo);
        return ResultEntity.ok().page(build.getTotal(), build.getSize(), build.getCurrent()).data("items", entity.getRecords());
    }

    @DeleteMapping("/delete")
    public ResultEntity delete(String id) {
        String returnId = service.removeCourseInfoById(id);
        return ResultEntity.ok().data("id", returnId);
    }
}

