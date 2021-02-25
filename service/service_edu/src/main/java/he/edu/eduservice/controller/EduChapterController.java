package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduChapter;
import he.edu.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @auth or aidawone
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
    public ResultEntity index(String id) {
        List<EduChapter> items = service.treeChapterVideoById(id);
        return ResultEntity.ok().data("items", items);
    }


    @PostMapping("/add")
    public ResultEntity add(@RequestBody EduChapter chapter) {
        String id = service.saveChapter(chapter);
        return ResultEntity.ok().data("id", id);
    }

    @PutMapping("/update")
    public ResultEntity update(@RequestBody EduChapter chapter) {
        String returnId = service.updateChapterById(chapter);
        return ResultEntity.ok().data("id", returnId);
    }

    @DeleteMapping("/delete")
    public ResultEntity delete(String id) {
        String returnId = service.deleteChapterById(id);
        return ResultEntity.ok().data("items", returnId);
    }

    @GetMapping("/detail")
    public ResultEntity detail(String id) {
        EduChapter chapter = service.detail(id);
        return ResultEntity.ok().data("items", chapter);
    }
}

