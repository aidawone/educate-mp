package he.edu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:15 2021/2/25
 */
@Data
@ApiModel(value = "课程发布信息")
public class CoursePuishVo {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String childrenName;
        private String parentName;
    private String name;
    private String price;//只用于显示
}
