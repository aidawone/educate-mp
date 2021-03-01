package he.edu.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.cms.entity.EduTeacher;
import he.edu.cms.service.EduTeacherService;
import he.edu.commonutils.entity.ResultEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/front/teacher")
@CrossOrigin
public class EduTeacherController {

    final EduTeacherService service;

    public EduTeacherController(EduTeacherService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public ResultEntity index(Long page, Long size) {
        Page<EduTeacher> build = new Page<>(page, size);
        Map<String, Object> map = service.getPageTeacher(build);
        return ResultEntity.ok().data(map);
    }

    @GetMapping("/detail")
    public ResultEntity detail(String id) {
        Map<String, Object> map = service.getTeacherInfoById(id);
        return ResultEntity.ok().data(map);
    }

}

