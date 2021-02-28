package he.edu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import he.edu.cms.entity.EduCourse;
import he.edu.cms.mapper.EduCourseMapper;
import he.edu.cms.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Override
    public List<EduCourse> getCourseHot() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0)
                .eq("status", "Normal")
                .orderByDesc("view_count", "buy_count")
                .last("limit 8");

        return this.list(wrapper);
    }
}
