package he.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.eduservice.entity.EduCourse;
import he.edu.eduservice.entity.EduCourseDescription;
import he.edu.eduservice.entity.vo.EduCourseVo;
import he.edu.eduservice.mapper.EduCourseMapper;
import he.edu.eduservice.service.EduCourseDescriptionService;
import he.edu.eduservice.service.EduCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    public EduCourseServiceImpl(EduCourseDescriptionService service) {
        this.service = service;
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
}
