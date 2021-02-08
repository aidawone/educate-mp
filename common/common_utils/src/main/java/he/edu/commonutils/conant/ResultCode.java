package he.edu.commonutils.conant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回状态码
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 15:33 2021/2/7
 */
@Api(description = "返回状态码")
public interface ResultCode {

    @ApiModelProperty(value = "成功")
    public static Integer SUCCESS = 20000;

    @ApiModelProperty(value = "失败")
    public static Integer ERROR = 20001;
}
