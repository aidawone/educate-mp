package he.edu.oss.service.impl;

import he.edu.oss.service.FileService;
import he.edu.oss.utils.FileUtils;
import he.edu.oss.utils.OssPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:10 2021/2/23
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 文件流上传
     *
     * @param file
     * @return
     */
    @Override
    public String Fileupload(MultipartFile file) {
        //设置目录
        String path = new DateTime().toString("yyyy/MM/dd");
        char pathChar = OssPropertiesUtils.FILE_PATH.charAt(OssPropertiesUtils.FILE_PATH.length() - 1);

        //生成的目录结构
        String fileLocalPath = "";
        //判读路径
        if (pathChar == '/') {
            fileLocalPath = OssPropertiesUtils.FILE_PATH + path;
        } else {
            fileLocalPath = OssPropertiesUtils.FILE_PATH + "/" + path;
        }

        return FileUtils.fileIo(fileLocalPath, file);
    }
}
