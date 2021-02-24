package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}

