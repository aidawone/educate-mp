package he.edu.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import he.edu.cms.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getTeacherHot();

    Map<String, Object> getPageTeacher(Page<EduTeacher> build);

    Map<String, Object> getTeacherInfoById(String id);
}
