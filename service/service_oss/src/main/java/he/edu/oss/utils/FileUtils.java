package he.edu.oss.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:39 2021/2/23
 */
public class FileUtils {

    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 生成文件名
     *
     * @return
     */
    public static String createDir(MultipartFile file) {
        //获得文件全称
        String fileName = file.getOriginalFilename();
        //获得文件名
        String original = "";
        String format = "";
        if (fileName != null) {
            original = fileName.substring(0, fileName.lastIndexOf("."));
            //获得文件格式
            format = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        //将时间戳和文件组成
        long millis = new DateTime().getMillis();
        return original + "-" + millis + "." + format;
    }

    /**
     *上传到指定位置
     * @param path 文件存储到磁盘的路径
     * @param file 文件
     * @return
     */
    public static String fileIo(String path, MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        File fie = new File(path);
        FileOutputStream fos = null;
        InputStream in = null;

        //生成文件名
        String fileName = FileUtils.createDir(file);
        if (!fie.exists()) {
            //创建文件夹
            fie.mkdirs();
        }

        try {
            fos = new FileOutputStream(path + "/" + fileName);
            in = file.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return path + "/" + fileName;
    }
}
