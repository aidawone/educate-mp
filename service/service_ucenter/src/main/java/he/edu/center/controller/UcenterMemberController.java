package he.edu.center.controller;


import he.edu.center.entity.UcenterMember;
import he.edu.center.service.UcenterMemberService;
import he.edu.commonutils.entity.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author aidawone
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/api/member")
public class UcenterMemberController {

    final UcenterMemberService memberService;

    public UcenterMemberController(UcenterMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/detail")
    public ResultEntity detail(String id) {
        UcenterMember member = memberService.getUserById(id);
        return ResultEntity.ok().data("user", member);
    }

    //统计每天创建的人数
    @GetMapping("/loginNum")
    public ResultEntity loginNum(String day) {
        Long count = memberService.countMemberByDay(day);
        return ResultEntity.ok().data("loginNum", count);
    }
}

