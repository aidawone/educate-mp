package he.edu.eduservice.entity.vo;

import he.edu.commonutils.entity.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 21:48 2021/2/25
 */
@Data
@ApiModel(value = "课程查询", description = "编辑课程的表单对象")
public class CourseVo extends PageModel {

    @ApiModelProperty(value = "课程ID")
    private String status;

    @ApiModelProperty(value = "课程名称")
    private String name;

}
