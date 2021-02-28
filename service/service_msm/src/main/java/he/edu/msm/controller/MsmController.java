package he.edu.msm.controller;

import he.edu.commonutils.entity.ResultEntity;
import he.edu.msm.service.MsmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:49 2021/2/28
 */
@RestController
@RequestMapping("/ali/msm")
public class MsmController {

    final MsmService msmService;

    public MsmController(MsmService msmService) {
        this.msmService = msmService;
    }

    @GetMapping("/send")
    public ResultEntity send(String phone) {
        msmService.sendMessage(phone);
        return ResultEntity.ok();
    }
}
