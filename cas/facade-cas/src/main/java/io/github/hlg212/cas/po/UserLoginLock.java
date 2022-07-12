package io.github.hlg212.cas.po;

import io.github.hlg212.fcf.model.Model;
import lombok.Data;

import java.util.Date;

@Data
public class UserLoginLock extends Model {
    private String username;
    private String errorMsg;
    private Date createTime;
    private String createUser;
    private Date lockToTime;
}
