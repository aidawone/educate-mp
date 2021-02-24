package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduChapter;
import he.edu.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/back/chapter")
@CrossOrigin
public class EduChapterController {

    final EduChapterService service;

    public EduChapterController(EduChapterService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public ResultEntity index(String id){
        List<EduChapter> items =  service.treeChapterVideoById(id);
        return ResultEntity.ok().data("items",items);
    }
}

