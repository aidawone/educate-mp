package he.edu.msm.controller.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import he.edu.commonutils.entity.HeException;

import java.util.Map;

/**
 * aliyun短信服务
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 17:13 2021/2/28
 */
public class MsmUtils {

    public static void send(String phone, Map<String, Object> param) {

        DefaultProfile profile =
                DefaultProfile.getProfile("default", MsmPropertiesUtils.KEY_ID, MsmPropertiesUtils.KEY_ID);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", MsmPropertiesUtils.SIGN_NAME); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", MsmPropertiesUtils.TEMPLATE_CODE); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            throw new HeException(20001, "阿里云发送短信失败！");
        }
    }

}
