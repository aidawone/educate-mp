package he.edu.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import he.edu.center.entity.UcenterMember;
import he.edu.center.mapper.UcenterMemberMapper;
import he.edu.center.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.utils.JwtUtils;
import he.edu.commonutils.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    public UcenterMemberServiceImpl() {

    }

    @Override
    public String loginByPassword(UcenterMember member) {
        // TODO 将token保存到redis中
        if (!StringUtils.isEmpty(member.getMobile()) && StringUtils.isEmpty(member.getPassword())) {
            throw new HeException(20001, "用户或者密码不能为空！");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("is_disabled", 0)
                .eq("is_deleted", 0)
                .eq("mobile", member.getMobile());
        UcenterMember entity = this.getOne(wrapper);

        if (StringUtils.isEmpty(entity)) {
            throw new HeException(20001, "该用户不存在或者已被禁用");
        }

        //判断密码
        if (!member.getPassword().equals(entity.getPassword())) {
            throw new HeException(20001, "用户或者密码错误！");
        }
        return JwtUtils.getJwtToken(entity.getId(), entity.getNickname());
    }

    //注册业务
    @Override
    public void registerUser(UcenterMember member) {
        if (StringUtils.isEmpty(member)) {
            throw new HeException(20001, "参数不可为空");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", member.getMobile());
        UcenterMember one = this.getOne(wrapper);
        if (!StringUtils.isEmpty(one)) {
            throw new HeException(20001, "当前用户已经存在");
        }
        boolean save = this.save(member);
        //TODO 将密码加密
        member.setPassword(MD5.encrypt(member.getPassword()));
        if (!save) {
            throw new HeException(20001, "用户注册失败！");
        }
    }

    @Override
    public UcenterMember infoParseToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)) {
            throw new HeException(20001, "无效token");
        }
        UcenterMember entity = this.getById(id);
        if (StringUtils.isEmpty(entity)) {
            throw new HeException(20001, "用户不存在");
        }
        entity.setPassword("");
        return entity;
    }

    @Override
    public UcenterMember getUcenterByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return this.getOne(wrapper);

    }

    @Override
    public UcenterMember getUserById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new HeException(20001, "参数不可为空！");
        }
        UcenterMember member = this.getById(id);
        if (StringUtils.isEmpty(member)) {
            throw new HeException(20001, "数据库中不存在该对象！");
        }

        return member;
    }

    @Override
    public Long countMemberByDay(String day) {
        if (StringUtils.isEmpty(day)) {
            throw new HeException(20001, "参数不可为空！");
        }
        Long num = baseMapper.countUcenterByDay(day);
        return num;
    }
}
