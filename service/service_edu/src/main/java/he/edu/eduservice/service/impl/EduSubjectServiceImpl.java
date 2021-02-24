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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    //组成树形结构
    @Override
    public List<EduSubject> treeMap() {
        //1.首先查询出所有的数据
        List<EduSubject> entities = this.list(null);

        //2.找出所有的1级别分类
        List<EduSubject> parentMenu = new ArrayList<>();
        for (EduSubject sub : entities) {
            if ("0".equals(sub.getParentId())) {
                parentMenu.add(sub);
            }
        }

        //递归查询
        for (EduSubject menu : parentMenu) {
            menu.setChild(getChildrens(menu, entities));
        }
        parentMenu.sort(new Comparator<EduSubject>() {
            @Override
            public int compare(EduSubject o1, EduSubject o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });

        return parentMenu;
    }

    public List<EduSubject> getChildrens(EduSubject root, List<EduSubject> entities) {
        List<EduSubject> childrens = new ArrayList<>();
        for (EduSubject entity : entities) {
            if (entity.getParentId().equals(root.getId())) {
                childrens.add(entity);
                entity.setChild(getChildrens(entity, entities));
            }
        }
        childrens.sort(new Comparator<EduSubject>() {
            @Override
            public int compare(EduSubject o1, EduSubject o2) {
                return (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort());
            }
        });

        return childrens;
    }
}
