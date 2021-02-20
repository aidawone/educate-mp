package he.edu.baseservice.handler;

import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.entity.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:16 2021/2/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity error(Exception e) {
        e.printStackTrace();
        return ResultEntity.error().message("error");
    }


    @ExceptionHandler(HeException.class)
    @ResponseBody
    public ResultEntity error(HeException e) {
        e.printStackTrace();
        return ResultEntity.error().code(e.getCode()).message(e.getMsg());
    }

}
