package com.feng.community.utils;

import static com.feng.community.constant.ResultViewCode.NEED_LOGIN;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import org.springframework.stereotype.Component;

/**
 * @author fengyunan
 * Created on 2021-03-10
 */
@Component
public class TokenUtils {
    private static String SECRET;

    // set注入密钥
    @Value("08fIptMkiWY8hHUt")
    public void setSecret(String SECRET) {
        this.SECRET = SECRET;
    }

    public static String getToken(TbUser user) {
        return JWT.create()
                .withIssuer("AriesUser")
                .withClaim("name", user.getUserName())
                .withClaim("id", user.getId())
                .withClaim("avatarUrl", user.getAvatarUrl())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000 * 24)) // 24小时
                .sign(Algorithm.HMAC256(SECRET));// token 的密钥
    }

    public static ResultView verifyToken(String token) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("AriesUser").build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Map<String, Claim> map = verify.getClaims();
            TbUser user = new TbUser();
            user.setId(map.get("id").asLong());
            user.setUserName(map.get("name").asString());
            user.setAvatarUrl(map.get("avatarUrl").asString());
            user.setEmail(map.get("email").asString());
            Map<String, String> resultMap = new HashMap<>(map.size());
            map.forEach((k, v) -> resultMap.put(k, v.asString()));
            return ResultView.success(user);

        } catch (JWTVerificationException e) {
            return ResultView.fail(NEED_LOGIN.getCode(), NEED_LOGIN.getMsg());
        }
    }
}
