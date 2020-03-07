package com.sunny.maven.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @description: 客户端密码测试类
 * @author: Sunny
 * @date: 2019/10/9
 */
public class PfOauth2TestSecret {
    public static void main(String ...args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String srcSource = "admin$123456";
        System.out.println("BCrypt加密：原文[" + srcSource + "]，加密["
                + passwordEncoder.encode(srcSource) + "]");
        BASE64Encoder encoder = new BASE64Encoder();
        String secBase64 = "paashsp-gateway:PAASHsp&Gateway";
        String base64Str = encoder.encode(secBase64.getBytes());
        System.out.println("Base64转码：" + base64Str);
        /**
         * {"access_token":
         * "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
         * eyJ1c2VyX25hbWUiOiJqb2huLmNhcm5lbGwiLCJzY29wZSI6WyJ3ZWJjbGllbnQiLCJtb2J
         * pbGVjbGllbnQiXSwibG9naW5OYW1lIjoiam9obi5jYXJuZWxsIiwiZXhwIjoxNTcwNjU0MTMyLCJqdGkiOiJhZTM1
         * ZGUwZi0xYzRmLTRiNzAtYmRhZS1kMTRjNDhiZDc0MTgiLCJjbGllbnRfaWQiOiJhZG1pbjEiLCJ0aW1lc3RhbXAiOjE1NzA2
         * MTA5MzI4MTl9.
         * 6clnZHxCqZSGAOsu-LR3NmpY4KjuHJwNGdILYdJEfvE",
         * "token_type":"bearer",
         * "refresh_token":
         * "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
         * eyJ1c2VyX25hbWUiOiJqb2huLmNhcm5lbGwiLCJzY29wZSI6WyJ3ZWJjbGllbnQiLCJtb2JpbGVjbGllbnQiXSwib
         * G9naW5OYW1lIjoiam9obi5jYXJuZWxsIiwiYXRpIjoiYWUzNWRlMGYtMWM0Zi00YjcwLWJkYWUtZDE0YzQ4YmQ3NDE4I
         * iwiZXhwIjoxNTczMjAyOTMyLCJqdGkiOiJiNWU4M2JkMi0wZmJlLTRjMDYtYjY4Zi1jYWMwZGYwMWM1YmEiLCJjbGllbnRf
         * aWQiOiJhZG1pbjEiLCJ0aW1lc3RhbXAiOjE1NzA2MTA5MzI4MTl9.
         * eoANVA97qhIQAZNFZsgURuieBx6znoDySCQbqQWvtJ8",
         * "expires_in":43199,
         * "scope":"webclient mobileclient",
         * "timestamp":1570610932819,
         * "loginName":"john.carnell",
         * "jti":"ae35de0f-1c4f-4b70-bdae-d14c48bd7418"}
         */
        BASE64Decoder decoder = new BASE64Decoder();
        System.out.println(new String(decoder.decodeBuffer("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                , "utf-8"));
        System.out.println(new String(decoder.decodeBuffer("eyJ1c2VyX25hbWUiOiJqb2huLmNhcm5lbGwiLCJzY29wZSI6WyJ3ZWJjbGllbnQiLCJtb2JpbGVjbGllbnQiXSwibG9naW5OYW1lIjoiam9obi5jYXJuZWxsIiwiZXhwIjoxNTcwODYwMTM3LCJqdGkiOiJhMGFjMTRhYi1hMjJjLTRlNzYtYmZkOS05YjZmM2I5NGYxMjciLCJjbGllbnRfaWQiOiJhZG1pbjEiLCJ0aW1lc3RhbXAiOjE1NzA4NTk1MzcxNTl9"), "utf-8"));
//        System.out.println(new String(decoder.decodeBuffer("6clnZHxCqZSGAOsu-LR3NmpY4KjuHJwNGdILYdJEfvE")
//                , "utf-8"));
    }
}
