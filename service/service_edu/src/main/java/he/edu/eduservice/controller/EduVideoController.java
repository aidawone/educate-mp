package he.edu.eduservice.controller;


import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.client.VodClient;
import he.edu.eduservice.entity.EduVideo;
import he.edu.eduservice.service.EduVideoService;
import org.springframework.util.StringUtils;
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
    final VodClient vodClient;

    public EduVideoController(EduVideoService service, VodClient vodClient) {
        this.vodClient = vodClient;
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
        //删除小节的时候删除视频
        //先去查询
        EduVideo video = service.getById(id);
        if (StringUtils.isEmpty(video)) {
            throw new HeException(20001, "数据库对象不存在！");
        }
        if(!StringUtils.isEmpty(video.getVideoSourceId())){
            vodClient.delete(video.getVideoSourceId());
        }
        String returnId = service.deleteById(id);

        return ResultEntity.ok().data("id", returnId);
    }
}

