package he.edu.commonutils.entity;

import he.edu.commonutils.conant.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应体
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 15:35 2021/2/7
 */
@Data
@Api(description = "响应体")
public class ResultEntity {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息体")
    private String message;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data = new HashMap<>();

    private ResultEntity() {
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultEntity ok() {
        ResultEntity entity = new ResultEntity();
        entity.setSuccess(true);
        entity.setCode(ResultCode.SUCCESS);
        entity.setMessage("success");
        return entity;
    }

    /**
     * 失败
     *
     * @return
     */
    public static ResultEntity error() {
        ResultEntity entity = new ResultEntity();
        entity.setSuccess(false);
        entity.setCode(ResultCode.ERROR);
        entity.setMessage("error");
        return entity;
    }


    public ResultEntity success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ResultEntity message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultEntity code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ResultEntity data(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    public ResultEntity data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    /**
     * 返回页码
     * @param total 总条数
     * @return
     */
    public ResultEntity total(Integer total) {
        this.data.put("total", total);
        return this;
    }
}
