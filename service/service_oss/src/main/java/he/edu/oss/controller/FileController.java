package he.edu.oss.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.oss.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:10 2021/2/23
 */
@RestController
@RequestMapping("/back/file")
@CrossOrigin
public class FileController {

    final private FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResultEntity upload(MultipartFile file) {
        String url = service.Fileupload(file);

        return ResultEntity.ok().data("url", url);
    }
}
