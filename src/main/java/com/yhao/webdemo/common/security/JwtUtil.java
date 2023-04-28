package com.yhao.webdemo.common.security;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class JwtUtil {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.id}")
    private String jwtId;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public final int TIME_UNIT = 1000;

    public String createJwt(Map<String, Object> claims, Long time) {
        // 签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date nowDate = new Date(System.currentTimeMillis());
        SecretKey secretKey = generateKey();
        // 生成jwt的时间
        long nowTime = System.currentTimeMillis();
        // jwt的payload内容
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setId(jwtId)
                .setIssuedAt(nowDate)
                .signWith(secretKey, signatureAlgorithm);

        if (time > 0) {
            long expireTime = nowTime + time;
            jwtBuilder.setExpiration(new Date(expireTime));
        }
        return jwtBuilder.compact();
    }

    public Claims verifyJwt(String token) {
        SecretKey secretKey = generateKey();
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJwt(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    public String generateToken(Long userId, String username) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("username", username);
        return createJwt(map, expiration * TIME_UNIT);
    }

    public String generateToken(String username) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("username", username);
        return createJwt(map, expiration * TIME_UNIT);
    }

    private SecretKey generateKey() {
        String stringKey = jwtSecret;
        byte[] encodedKey = BaseEncoding.base64().decode(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }
}
