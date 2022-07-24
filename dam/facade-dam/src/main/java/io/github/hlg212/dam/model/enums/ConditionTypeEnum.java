package io.github.hlg212.dam.model.enums;

import io.github.hlg212.fcf.model.BaseEnum;
import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum ConditionTypeEnum implements BaseEnum {

    EXTEND("EXTEND","扩展"),
    SYS("SYS","系统"),
    ONLY("ONLY","指定");

    private String value;
    private String desc;

    ConditionTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
