package he.edu.cms.controller;


import he.edu.cms.service.EduChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/cms/chapter")
public class EduChapterController {
    final EduChapterService service;

    public EduChapterController(EduChapterService service) {
        this.service = service;
    }

}

