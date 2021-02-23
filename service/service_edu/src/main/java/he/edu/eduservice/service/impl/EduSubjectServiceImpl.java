package he.edu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.eduservice.entity.EduSubject;
import he.edu.eduservice.excel.ExcelSubject;
import he.edu.eduservice.listener.SubjectListener;
import he.edu.eduservice.mapper.EduSubjectMapper;
import he.edu.eduservice.service.EduSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectListener.class);

    @Override
    public void importByExcel(MultipartFile file, EduSubjectService subjectService) {
        try {
            EasyExcel.read(file.getInputStream(), ExcelSubject.class, new SubjectListener(subjectService))
                    .sheet().doRead();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public EduSubject existByName(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        return this.getOne(wrapper);
    }
}
