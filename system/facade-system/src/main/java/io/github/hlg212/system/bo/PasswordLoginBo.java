package io.github.hlg212.system.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

/**
 * @author: Administrator
 * @date: 2022/7/10 13:07
 */
@Data
public class PasswordLoginBo {

    @Field(description="账号")
    private String username;
    @Field(description="密码")
    private String password;
    @Field(description="应用代码")
    private String appCode;
    @Field(description="账号密码编码")
    private String usernamePassword;
    @Field(description="是否编码")
    private String isEncode;
}
