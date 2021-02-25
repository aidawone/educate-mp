package he.edu.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.eduservice.entity.EduCourse;
import he.edu.eduservice.entity.vo.CoursePuishVo;
import he.edu.eduservice.entity.vo.CourseVo;
import he.edu.eduservice.entity.vo.EduCourseVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    EduCourseVo getCourseInoById(@Param("id") String id);

    CoursePuishVo getCoursePublishById(@Param("id") String id);

    Page<EduCourse> getEntityByConditions(@Param("build") Page<EduCourse> build,@Param("courseVo") CourseVo courseVo);
}
