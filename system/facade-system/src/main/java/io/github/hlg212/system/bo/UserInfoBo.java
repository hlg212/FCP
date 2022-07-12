package io.github.hlg212.system.bo;

import io.github.hlg212.fcf.model.basic.IUser;
import lombok.Data;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/7/10 13:52
 */
@Data
public class UserInfoBo {

    private IUser user;

    private List<String> permissions;

}
