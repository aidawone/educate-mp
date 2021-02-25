package he.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.eduservice.entity.EduVideo;
import he.edu.eduservice.mapper.EduVideoMapper;
import he.edu.eduservice.service.EduVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EduChapterServiceImpl.class);

    @Override
    public EduVideo detail(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        EduVideo video = this.getOne(wrapper);
        if (StringUtils.isEmpty(video)) {
            LOGGER.error("数据库中不存在该对象");
            throw new HeException(20001, "数据库中不存在该对象!");
        }
        return video;
    }

    @Override
    public String addVideo(EduVideo video) {
        if (StringUtils.isEmpty(video)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }

        boolean save = this.save(video);
        if (!save) {
            LOGGER.error("插入小节失败！");
            throw new HeException(20001, "新增操作失败！");
        }
        return video.getId();
    }

    @Override
    public String updateVideo(EduVideo video) {
        if (StringUtils.isEmpty(video)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }

        boolean save = this.updateById(video);
        if (!save) {
            LOGGER.error("插入小节失败！");
            throw new HeException(20001, "新增操作失败！");
        }
        return video.getId();
    }

    @Override
    public String deleteById(String id) {
        //TODO 删除视频

        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }

        boolean save = this.removeById(id);
        if (!save) {
            LOGGER.error("删除小节失败！");
            throw new HeException(20001, "删除小节失败！");
        }
        return id;
    }
}
