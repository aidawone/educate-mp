package he.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import he.edu.eduservice.entity.EduCourse;
import he.edu.eduservice.entity.vo.CoursePuishVo;
import he.edu.eduservice.entity.vo.CourseVo;
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

    EduCourseVo getCourseInfoById(String id);

    String updateCourse(EduCourseVo id);

    CoursePuishVo getCoursePublish(String id);

    String publishById(String id);

    String removeCourseInfoById(String id);

    Page<EduCourse> entityByConditions(Page<EduCourse> build, CourseVo courseVo);
}
