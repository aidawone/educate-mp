package he.edu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import he.edu.oss.service.OssFileService;
import he.edu.oss.utils.OssPropertiesUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:28 2021/2/23
 */
@Service
public class OssFileServiceImpl implements OssFileService {

    private final static Logger log = LoggerFactory.getLogger(OssFileServiceImpl.class);

    @Override
    public String aliyunFileUpload(MultipartFile file) {
        String endpoint = OssPropertiesUtils.END_POINT;
        String keyId = OssPropertiesUtils.KEY_ID;
        String keySecret = OssPropertiesUtils.KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = null;
        String path = "";
        String fileName = "";
        InputStream is = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
            // 上传文件流。
            is = file.getInputStream();
            fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replace("-", "");
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            fileName = dateTime + "/" + uuid + fileName;

            ossClient.putObject(bucketName, fileName, is);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        path = "https://" + bucketName + "." + endpoint + "/" + fileName;

        return path;
    }
}
