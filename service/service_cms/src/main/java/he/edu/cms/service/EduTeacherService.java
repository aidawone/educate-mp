package he.edu.cms.service;

import he.edu.cms.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
