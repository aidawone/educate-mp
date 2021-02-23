package he.edu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:06 2021/2/23
 */
public interface FileService {
    String Fileupload(MultipartFile file);
}
