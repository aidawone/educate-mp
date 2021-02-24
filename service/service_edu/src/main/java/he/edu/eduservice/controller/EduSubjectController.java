package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduSubject;
import he.edu.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/back/subject")
@CrossOrigin
public class EduSubjectController {

    final EduSubjectService subjectService;

    public EduSubjectController(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/import")
    public ResultEntity lead(MultipartFile file) {
        subjectService.importByExcel(file, subjectService);
        return ResultEntity.ok();
    }

    @GetMapping("/index")
    public ResultEntity index() {
        List<EduSubject> listMap = subjectService.treeMap();
        return ResultEntity.ok().data("items", listMap);
    }

}

