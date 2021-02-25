package he.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.eduservice.entity.EduChapter;
import he.edu.eduservice.entity.EduVideo;
import he.edu.eduservice.mapper.EduChapterMapper;
import he.edu.eduservice.service.EduChapterService;
import he.edu.eduservice.service.EduVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
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
        videoQueryWrapper.select("id", "title", "chapter_id");
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

    @Override
    public String saveChapter(EduChapter chapter) {
        if (StringUtils.isEmpty(chapter)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }
        boolean save = this.save(chapter);
        if (!save) {
            LOGGER.error("章节插入数据库失败！");
            throw new HeException(20001, "新增章节失败！");
        }
        return chapter.getId();
    }

    @Override
    public String updateChapterById(EduChapter chapter) {

        if (StringUtils.isEmpty(chapter) && StringUtils.isEmpty(chapter.getId())) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("id", chapter.getId());
        List<EduChapter> list = this.list(wrapper);

        if (list.size() == 0) {
            LOGGER.error("数据库中不存在该对象！");
            throw new HeException(20001, "数据库中不存在该对象！");
        }
        boolean flag = this.updateById(chapter);

        if (!flag) {
            LOGGER.error("数据库中不存在该对象！");
            throw new HeException(20001, "更新数据库失败！");
        }
        return chapter.getId();
    }

    @Override
    public String deleteChapterById(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }

        //查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        List<EduVideo> videos = service.list(wrapper);

        if (videos.size() > 0) {
            LOGGER.error("该章节下有小节,无法删除章节！");
            throw new HeException(20001, "该章节下有小节,无法删除章节!");
        } else {
            boolean flag = this.removeById(id);
            if (!flag) {
                LOGGER.error("删除章节失败");
                throw new HeException(20001, "删除章节失败!");
            }
        }
        return id;
    }

    @Override
    public EduChapter detail(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不能为空");
            throw new HeException(20001, "参数不能为空!");
        }
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .select("id", "course_id", "title", "sort", "gmt_create");

        EduChapter chapter = this.getOne(wrapper);
        if (StringUtils.isEmpty(chapter)) {
            LOGGER.error("数据库中不存在该对象");
            throw new HeException(20001, "数据库中不存在该对象!");
        }
        return chapter;
    }
}
