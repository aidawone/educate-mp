package he.edu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:28 2021/2/23
 */
public interface OssFileService {
    String aliyunFileUpload(MultipartFile file);
}
