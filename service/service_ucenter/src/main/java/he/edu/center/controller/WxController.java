package he.edu.center.controller;

import he.edu.center.service.WxService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:30 2021/3/1
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxController {

    final WxService service;

    public WxController(WxService service) {
        this.service = service;
    }

    @GetMapping("/callback")
    public String callback(String code, String state) {
        String token = service.wxCodeCallback(code, state);
        return "redirect:http://localhost:3000?token="+token;
    }

    @GetMapping("/login")
    public String login() {
        String url = service.wxToQrCodeUrl();
        return "redirect:" + url;
    }
}

