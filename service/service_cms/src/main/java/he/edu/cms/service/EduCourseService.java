package he.edu.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.cms.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import he.edu.cms.entity.vo.EduCourseDetailVo;
import he.edu.cms.entity.vo.EduCourseVo;

import java.util.List;
import java.util.Map;

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

    Map<String, Object> ListByConditionsAndOrder(Page<EduCourse> build, EduCourseVo courseVo);

    EduCourseDetailVo getCourseDetailById(String id);
}
