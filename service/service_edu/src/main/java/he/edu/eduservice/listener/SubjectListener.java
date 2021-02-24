package he.edu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import he.edu.eduservice.entity.EduSubject;
import he.edu.eduservice.excel.ExcelSubject;
import he.edu.eduservice.service.EduSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 21:51 2021/2/23
 */
public class SubjectListener extends AnalysisEventListener<ExcelSubject> {

    final EduSubjectService subjectService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectListener.class);

    public SubjectListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
        //判断
        if (StringUtils.isEmpty(excelSubject)) {
            LOGGER.warn("excel文件数据为空");
            return;
        }
        //判断父级是否存在
        EduSubject parentEntity = new EduSubject();
        parentEntity.setId("0");
        if (!StringUtils.isEmpty(excelSubject.getParentName())) {
            parentEntity = subjectService.existByName(excelSubject.getParentName());
            if (StringUtils.isEmpty(parentEntity)) {
                parentEntity = new EduSubject();
                parentEntity.setTitle(excelSubject.getParentName());
                boolean save = subjectService.save(parentEntity);
            }
        }

        //判读课程是否存在
        EduSubject entity = subjectService.existByName(excelSubject.getName());
        if (StringUtils.isEmpty(entity)) {
            //不存在
            EduSubject subject = new EduSubject();
            subject.setParentId(parentEntity.getId());
            subject.setTitle(excelSubject.getName());
            subject.setSort(excelSubject.getSort());
            subjectService.save(subject);
        } else {
            entity.setParentId(parentEntity.getId());
            entity.setSort(parentEntity.getSort());
            subjectService.updateById(entity);
        }
        LOGGER.info("开始存储数据库！");

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("课程数据已经导入成功！");
    }
}
