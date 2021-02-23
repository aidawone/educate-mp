package he.edu.oss.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.oss.service.OssFileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:28 2021/2/23
 */
@RestController
@RequestMapping("/oss/original")
@CrossOrigin
public class OssFileController {

    final OssFileService service;

    public OssFileController(OssFileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResultEntity upload(MultipartFile file) {
        String path = service.aliyunFileUpload(file);
        return ResultEntity.ok().data("url", path);
    }
}
