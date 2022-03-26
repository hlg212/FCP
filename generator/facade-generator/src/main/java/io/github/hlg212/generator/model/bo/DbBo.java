package io.github.hlg212.generator.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

@Data
public class DbBo {

    @Field(description = "数据库连接地址")
    private String dbUrl;
    @Field(description = "数据库账号")
    private String username;
    @Field(description = "数据库密码")
    private String password;

}
