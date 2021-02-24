package he.edu.oss.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.oss.service.FileService;
import he.edu.oss.utils.IpUtils;
import he.edu.oss.utils.OssPropertiesUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:10 2021/2/23
 */
@RestController
@RequestMapping("/oss/file")
@CrossOrigin
public class FileController {

    final private FileService service;

    private final static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResultEntity upload(MultipartFile file) {
        String url = service.Fileupload(file, "http://" + IpUtils.getLocalIpAddr() + ":" + OssPropertiesUtils.NGINX_PORT);

        return ResultEntity.ok().data("url", url);
    }


    @GetMapping(value = "/media")
    public void getDownload(String name, HttpServletRequest request, HttpServletResponse response) {

        // Get your file stream from wherever.
        String path = OssPropertiesUtils.FILE_PATH + name;
        File downloadFile = new File(path);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(path);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            LOGGER.info("context getMimeType is null");
        }
        LOGGER.info("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(path);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
