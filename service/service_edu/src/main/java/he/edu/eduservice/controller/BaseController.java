package he.edu.eduservice.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * crud控制器
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:20 2021/2/7
 */
public class BaseController {

    @Autowired
    BaseService baseService;

    @DeleteMapping("/status")
    public ResultEntity status(String id, Integer status) {
        return baseService.status(id, status);
    }

}
