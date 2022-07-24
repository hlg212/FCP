package io.github.hlg212.basic.model.enums;

import io.github.hlg212.fcf.model.BaseEnum;
import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum RoleTypeEnum implements BaseEnum {

    MANAGE("MANAGE","管理者"),
    USE("USE","使用者");

    private String value;
    private String desc;

    RoleTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
