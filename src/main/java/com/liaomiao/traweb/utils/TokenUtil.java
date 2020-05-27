package com.liaomiao.traweb.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.liaomiao.traweb.pojo.User;

public class TokenUtil {

    public static String getToken(User user) {
        String token = "token";
        token = JWT.create().withAudience(user.getUsername())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}