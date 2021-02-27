package he.edu.vod.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.vod.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频上传接口
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 20:11 2021/2/26
 */
@RestController
@CrossOrigin
@RequestMapping("/vod/video")
public class VideoController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResultEntity upload(MultipartFile file) {
        //文件原名称
        String stream = "";
        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            stream = videoService.uploadInput(title, fileName, file.getInputStream());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return ResultEntity.ok().data("hash", stream);

    }

    @DeleteMapping("/delete")
    public ResultEntity delete(String id) {
        videoService.deleteByIds(id);
        return ResultEntity.ok();
    }
}
