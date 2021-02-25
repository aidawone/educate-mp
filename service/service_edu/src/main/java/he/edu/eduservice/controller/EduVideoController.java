package he.edu.eduservice.controller;


import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.EduVideo;
import he.edu.eduservice.service.EduVideoService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/back/video")
@CrossOrigin
public class EduVideoController {

    final EduVideoService service;

    public EduVideoController(EduVideoService service) {
        this.service = service;

    }

    @GetMapping("/detail")
    public ResultEntity detail(String id) {
        EduVideo video = service.detail(id);
        return ResultEntity.ok().data("items", video);
    }

    @PostMapping("/add")
    public ResultEntity add(@RequestBody EduVideo video) {
        String returnId = service.addVideo(video);
        return ResultEntity.ok().data("id", returnId);
    }

    @PutMapping("/update")
    public ResultEntity update(@RequestBody EduVideo video) {
        String returnId = service.updateVideo(video);
        return ResultEntity.ok().data("id", returnId);
    }

    @DeleteMapping("/delete")
    public ResultEntity delete(String id) {
        String returnId = service.deleteById(id);
        return ResultEntity.ok().data("id", returnId);
    }


}

