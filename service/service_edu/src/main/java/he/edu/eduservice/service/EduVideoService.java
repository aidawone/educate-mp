package he.edu.eduservice.service;

import he.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
public interface EduVideoService extends IService<EduVideo> {

    EduVideo detail(String id);

    String addVideo(EduVideo video);

    String updateVideo(EduVideo video);

    String deleteById(String id);
}
