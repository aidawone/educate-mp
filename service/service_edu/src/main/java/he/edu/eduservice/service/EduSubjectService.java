package he.edu.eduservice.service;

import he.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-23
 */
public interface EduSubjectService extends IService<EduSubject> {

    //导入
    void importByExcel(MultipartFile file, EduSubjectService subjectService);

    //判断
    EduSubject existByName(String name);

    List<EduSubject> treeMap();
}
