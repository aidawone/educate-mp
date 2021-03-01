package he.edu.center.service;

import he.edu.center.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String loginByPassword(UcenterMember member);

    void registerUser(UcenterMember member);

    UcenterMember infoParseToken(HttpServletRequest request);

    UcenterMember getUcenterByOpenId(String openid);
}
