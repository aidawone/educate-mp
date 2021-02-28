package he.edu.cms.service;

import he.edu.cms.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
public interface EduCourseService extends IService<EduCourse> {

    List<EduCourse> getCourseHot();
}
