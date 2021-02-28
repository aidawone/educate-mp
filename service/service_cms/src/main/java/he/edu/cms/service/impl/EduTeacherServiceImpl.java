package he.edu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import he.edu.cms.entity.EduTeacher;
import he.edu.cms.mapper.EduTeacherMapper;
import he.edu.cms.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public List<EduTeacher> getTeacherHot() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0)
                .last("limit 4");
        return this.list(wrapper);
    }
}
