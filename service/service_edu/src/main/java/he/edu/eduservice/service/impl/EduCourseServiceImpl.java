package he.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.client.VodClient;
import he.edu.eduservice.entity.EduChapter;
import he.edu.eduservice.entity.EduCourse;
import he.edu.eduservice.entity.EduCourseDescription;
import he.edu.eduservice.entity.EduVideo;
import he.edu.eduservice.entity.vo.CoursePuishVo;
import he.edu.eduservice.entity.vo.CourseVo;
import he.edu.eduservice.entity.vo.EduCourseVo;
import he.edu.eduservice.mapper.EduCourseMapper;
import he.edu.eduservice.service.EduChapterService;
import he.edu.eduservice.service.EduCourseDescriptionService;
import he.edu.eduservice.service.EduCourseService;
import he.edu.eduservice.service.EduVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EduCourseServiceImpl.class);

    final EduCourseDescriptionService service;

    final EduCourseMapper mapper;
    final EduChapterService chapterService;
    final EduVideoService videoService;

    final VodClient vodClient;

    public EduCourseServiceImpl(EduCourseDescriptionService service,
                                EduCourseMapper mapper,
                                EduChapterService chapterService,
                                EduVideoService videoService,
                                VodClient vodClient) {


        this.service = service;
        this.mapper = mapper;
        this.videoService = videoService;
        this.chapterService = chapterService;

        this.vodClient = vodClient;
    }

    @Override
    public String saveCourseDesc(EduCourseVo vo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(vo, course);
        boolean save = this.save(course);
        if (!save) {
            LOGGER.error("新增课程失败");
            throw new HeException(20001, "添加课程信息失败");
        }

        //setting
        EduCourseDescription description = new EduCourseDescription();
        description.setId(course.getId());
        description.setDescription(vo.getDescription());

        boolean desc = service.save(description);
        if (!desc) {
            LOGGER.error("新增课程描述失败");
            throw new HeException(20001, "添加课程描述失败");
        }

        return course.getId();
    }

    @Override
    public EduCourseVo getCourseInfoById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new HeException(20001, "参数不可为null");
        }
        EduCourseVo courseVo = mapper.getCourseInoById(id);
        if (StringUtils.isEmpty(courseVo)) {
            throw new HeException(20001, "数据库中不存在该对象");
        }
        return courseVo;
    }

    @Override
    public String updateCourse(EduCourseVo vo) {
        //修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(vo, course);
        boolean flag = this.updateById(course);

        if (!flag) {
            throw new HeException(20001, "更新课程表失败了！");
        }
        //更新简介表
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(vo.getDescription());
        description.setId(course.getId());
        boolean dsc = service.updateById(description);
        if (!dsc) {
            throw new HeException(20001, "更新课程简介失败了！");
        }
        return vo.getId();
    }

    @Override
    public CoursePuishVo getCoursePublish(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不可为null!");
            throw new HeException(20001, "参数不可为null");
        }

        CoursePuishVo vo = mapper.getCoursePublishById(id);
        if (StringUtils.isEmpty(vo)) {
            LOGGER.error("数据库中不存在该对象!");
            throw new HeException(20001, "数据库中不存在该对象！");
        }
        return vo;
    }

    @Override
    public String publishById(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不可为null!");
            throw new HeException(20001, "参数不可为null");
        }
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        boolean flag = this.updateById(course);
        if (!flag) {
            LOGGER.error("更新数据失败!");
            throw new HeException(20001, "发布失败！");
        }
        return id;
    }

    @Override
    public String removeCourseInfoById(String id) {
        if (StringUtils.isEmpty(id)) {
            LOGGER.error("参数不可为null!");
            throw new HeException(20001, "参数不可为null");
        }

        //.先删除所有的视频
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> list = videoService.list(videoQueryWrapper);
        //1.删除课程
        String ids = list.stream().map(EduVideo::getVideoSourceId).collect(Collectors.joining(","));
        if (!StringUtils.isEmpty(ids)) {
            ResultEntity entity = vodClient.delete(ids);
            if (entity.getCode().equals(20001)) {
                LOGGER.error("删除视频..服务器请求超时！");
                throw new HeException(20001, "删除视频..服务器请求超时！！");
            }
        }

        //删除课程
        boolean courseFlag = this.removeById(id);
        if (!courseFlag) {
            LOGGER.error("删除课程失败!");
            throw new HeException(20001, "删除课程失败！");
        }
        //2.删除课程简介
        boolean descFlag = service.removeById(id);
        if (!descFlag) {
            LOGGER.error("删除课程简介失败!");
            throw new HeException(20001, "删除课程简介失败！");
        }
        //3.删除课程章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        boolean chapterFlag = chapterService.remove(chapterQueryWrapper);
        if (!chapterFlag) {
            LOGGER.error("删除课程章节失败!");
            throw new HeException(20001, "删除课程章节失败！");
        }
        //4.删除课程小节

        boolean videoFlag = videoService.remove(videoQueryWrapper);
        if (!videoFlag) {
            LOGGER.error("删除课程小节失败!");
            throw new HeException(20001, "删除课程小节失败！");
        }
        return id;
    }

    @Override
    public Page<EduCourse> entityByConditions(Page<EduCourse> build, CourseVo courseVo) {
        return mapper.getEntityByConditions(build, courseVo);
    }
}
