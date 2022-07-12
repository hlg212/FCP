package io.github.hlg212.cas.po;

import io.github.hlg212.fcf.model.Model;
import lombok.Data;

import java.util.Date;


@Data
public class LoginPasswordErrorLog extends Model {
    private String id;
    private String username; //用户账号
    private Date errorTime;    //登录时间
}
