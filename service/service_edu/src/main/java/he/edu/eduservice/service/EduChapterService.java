package he.edu.eduservice.service;

import he.edu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-24
 */
public interface EduChapterService extends IService<EduChapter> {

    List<EduChapter> treeChapterVideoById(String id);
}
