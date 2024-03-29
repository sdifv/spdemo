package com.yhao.webdemo.common.distLock;

import com.yhao.webdemo.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TokenUtil {

    @Autowired
    private RedisUtil redisUtil;

    private static final String TOKEN_IDENTIFY = "token_identify:";

    @SuppressWarnings("unchecked")
    public String generateToken(String value) {
        String token = UUID.randomUUID().toString();
        String key = TOKEN_IDENTIFY + token;
        redisUtil.setEx(key, value, 5, TimeUnit.SECONDS);
        return token;
    }

    public boolean validToken(String token, String value) {
        String lua = "if redis.call('get', KEYS[1])==KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
//        RedisScript<Long> redisScript = new DefaultRedisScript<>(lua, Long.class);
        String key = TOKEN_IDENTIFY + token;
//        Long resut = (Long) redisTemplate.execute(redisScript, Arrays.asList(key, value));
        // 根据返回结果判断是否成功成功匹配并删除 Redis 键值对，若果结果不为空和0，则验证通过
        if (Objects.equals(redisUtil.get(key), value)) {
            redisUtil.delete(key);
            log.info("验证 token={},key={},value={} 成功", token, key, value);
            return true;
        }
        log.info("验证 token={},key={},value={} 失败", token, key, value);
        return false;
    }

}
