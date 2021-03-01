package he.edu.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.cms.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import he.edu.cms.entity.vo.EduCourseDetailVo;
import he.edu.cms.entity.vo.EduCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    List<EduCourse> ListByConditionsAndOrder(@Param("build") Page<EduCourse> build, @Param("courseVo") EduCourseVo courseVo);

    EduCourseDetailVo selectDetailById(@Param("id") String id);
}
