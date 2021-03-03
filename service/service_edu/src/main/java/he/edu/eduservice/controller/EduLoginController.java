package he.edu.eduservice.controller;

import he.edu.commonutils.entity.ResultEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:11 2021/2/21
 */
@RestController
@RequestMapping("/back/user")
public class EduLoginController {

    @PostMapping("/login")
    public ResultEntity login() {

        return ResultEntity.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public ResultEntity info() {

        return ResultEntity.ok().data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
