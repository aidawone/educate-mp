package he.edu.vod.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import he.edu.commonutils.entity.HeException;
import he.edu.vod.service.VideoService;
import he.edu.vod.utils.VodClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 20:11 2021/2/26
 */
@Service
public class VideoServiceImpl implements VideoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Override
    public String uploadInput(String title, String fileName, InputStream inputStream) {
        String stream = VodClientUtils.uploadStream(title, fileName, inputStream);
        if (StringUtils.isEmpty(stream)) {
            throw new HeException(20001, "视频上传失败");
        }
        return stream;
    }

    @Override
    public void deleteByIds(String ids) {
        DeleteVideoResponse deleteVideoResponse = null;
        try {
            DefaultAcsClient client = VodClientUtils.initVodClient();
            VodClientUtils.deleteVideo(client, ids);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String getPlayAuth(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不能为空！");
            throw new HeException(20001, "参数不能为空！");
        }
        DefaultAcsClient client = VodClientUtils.initVodClient();
        GetVideoPlayAuthResponse videoPlayAuth = null;
        try {
            videoPlayAuth = VodClientUtils.getVideoPlayAuth(client, id);
        } catch (Exception e) {
            LOGGER.error("aliyun获取凭证失败！");
            throw new HeException(20001, "aliyun获取凭证失败！");
        }
        return videoPlayAuth.getPlayAuth();
    }
}
