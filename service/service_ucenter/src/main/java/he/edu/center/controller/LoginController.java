package he.edu.center.controller;

import he.edu.center.entity.UcenterMember;
import he.edu.center.service.UcenterMemberService;
import he.edu.commonutils.entity.ResultEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 17:55 2021/2/28
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class LoginController {
    final UcenterMemberService ucenterMemberService;

    public LoginController(UcenterMemberService ucenterMemberService) {
        this.ucenterMemberService = ucenterMemberService;

    }

    @GetMapping("/login")
    public ResultEntity login() {
        //保存盐值 TODO
        return ResultEntity.ok().data("salt", "");
    }

    @PostMapping("/login")
    public ResultEntity login(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.loginByPassword(member);
        return ResultEntity.ok().data("token", token);
    }

    @PostMapping("/register")
    public ResultEntity register(@RequestBody UcenterMember member) {
        ucenterMemberService.registerUser(member);
        return ResultEntity.ok();
    }

    @GetMapping("/info")
    public ResultEntity info(HttpServletRequest request) {
        UcenterMember member = ucenterMemberService.infoParseToken(request);
        return ResultEntity.ok().data("items", member);
    }

}
