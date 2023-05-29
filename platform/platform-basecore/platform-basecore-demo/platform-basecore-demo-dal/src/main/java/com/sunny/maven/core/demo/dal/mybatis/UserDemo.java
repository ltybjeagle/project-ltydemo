package com.sunny.maven.core.demo.dal.mybatis;

import com.sunny.maven.core.annotation.database.encryption.EncryptTransaction;
import com.sunny.maven.core.annotation.database.encryption.SensitiveData;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/5/26 14:53
 */
@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SensitiveData
public class UserDemo implements Serializable {
    @Serial
    private static final long serialVersionUID = 4810489817112532213L;

    /**
     * 数据id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址（表明对该字段进行加密）
     */
    @EncryptTransaction
    private String address;
}
