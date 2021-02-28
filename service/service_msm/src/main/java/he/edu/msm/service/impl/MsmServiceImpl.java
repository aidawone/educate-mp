package he.edu.msm.service.impl;

import he.edu.msm.controller.utils.MsmUtils;
import he.edu.msm.controller.utils.RandomUtil;
import he.edu.msm.service.MsmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:51 2021/2/28
 */
@Service
public class MsmServiceImpl implements MsmService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MsmServiceImpl.class);

    final RedisTemplate<String, String> redisTemplate;

    public MsmServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendMessage(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return;
        }
        //从redis中获得缓存
        String code = redisTemplate.opsForValue().get("code");
        if (StringUtils.isEmpty(code)) {
            return;
        }

        String random = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", random);
        //存入redis
        redisTemplate.opsForValue().set("code", random);
        //调用短信服务
        MsmUtils.send(phone, param);
    }
}
