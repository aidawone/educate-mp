package he.edu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.cms.entity.EduChapter;
import he.edu.cms.entity.EduVideo;
import he.edu.cms.mapper.EduChapterMapper;
import he.edu.cms.service.EduChapterService;
import he.edu.cms.service.EduVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EduChapterServiceImpl.class);

    final EduVideoService service;

    public EduChapterServiceImpl(EduVideoService service) {
        this.service = service;
    }

    @Override
    public List<EduChapter> treeChapterVideoById(String id) {
        //1.首先查询出所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        //查询的字段
        chapterQueryWrapper.select("id", "title");
        chapterQueryWrapper.eq("course_id", id);
        chapterQueryWrapper.orderByDesc("sort");

        List<EduChapter> chapters = this.list(chapterQueryWrapper);

        //2.然后查询出所有的小节
        //1.首先查询出所有的章节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        //查询的字段
        videoQueryWrapper.select("id", "title", "chapter_id","video_source_id");
        videoQueryWrapper.eq("course_id", id);
        videoQueryWrapper.orderByDesc("sort");
        List<EduVideo> videos = service.list(videoQueryWrapper);


        //3.遍历组装
        for (EduChapter chapter : chapters) {
            List<EduVideo> childrens = new ArrayList<>();
            for (EduVideo video : videos) {
                if (chapter.getId().equals(video.getChapterId())) {
                    childrens.add(video);
                }
            }

            chapter.setChild(childrens);
        }
        return chapters;
    }
}
