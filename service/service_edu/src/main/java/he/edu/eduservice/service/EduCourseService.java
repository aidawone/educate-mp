package he.edu.eduservice.service;

import he.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import he.edu.eduservice.entity.vo.EduCourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseDesc(EduCourseVo vo);
}
