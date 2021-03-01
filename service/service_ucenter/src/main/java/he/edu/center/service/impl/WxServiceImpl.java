package he.edu.center.service.impl;

import com.google.gson.Gson;
import he.edu.center.entity.UcenterMember;
import he.edu.center.service.UcenterMemberService;
import he.edu.center.service.WxService;
import he.edu.center.utils.HttpClientUtils;
import he.edu.center.utils.WxPropertiesUtils;
import he.edu.commonutils.entity.HeException;
import he.edu.commonutils.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:38 2021/3/1
 */
@Service
public class WxServiceImpl implements WxService {
    private final static Logger LOGGER = LoggerFactory.getLogger(WxServiceImpl.class);
    final UcenterMemberService service;

    public WxServiceImpl(UcenterMemberService service) {
        this.service = service;
    }

    @Override
    public String wxToQrCodeUrl() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = WxPropertiesUtils.REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new HeException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                WxPropertiesUtils.APP_ID,
                redirectUrl,
                state);
        return qrcodeUrl;
    }

    //得到扫码人信息
    @Override
    public String wxCodeCallback(String code, String state) {
        //1 获取code值，临时票据，类似于验证码
        //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个参数 ：id  秘钥 和 code值
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                WxPropertiesUtils.APP_ID,
                WxPropertiesUtils.APP_SECRET,
                code
        );
        String accessTokenInfo = "";
        try {
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            LOGGER.error("微信获取token失败");
            throw new HeException(20001, "请求接口失败");
        }

        //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
        //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
        //使用json转换工具 Gson
        Gson gson = new Gson();
        HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String) mapAccessToken.get("access_token");
        String openid = (String) mapAccessToken.get("openid");

        //判断数据库中是否存在值
        UcenterMember member = service.getUcenterByOpenId(openid);
        if (StringUtils.isEmpty(member)) { //为空。插入数据库
            //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //拼接两个参数
            String userInfoUrl = String.format(
                    baseUserInfoUrl,
                    accessToken,
                    openid
            );
            //发送请求
            String userInfo = "";
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                LOGGER.error("获取用户信息失败");
                throw new HeException(20001, "获取用户信息失败");
            }
            //获取返回userinfo字符串扫描人信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");//昵称
            String headimgurl = (String) userInfoMap.get("headimgurl");//头像

            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            boolean save = service.save(member);
            if (!save) {
                LOGGER.error("保存微信用户数据出错");
                throw new HeException(20001, "保存微信用户数据出错");
            }
        }
        //使用jwt根据member对象生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }
}
