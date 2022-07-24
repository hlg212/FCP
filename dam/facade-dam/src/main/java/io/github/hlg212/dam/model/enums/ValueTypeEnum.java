package io.github.hlg212.dam.model.enums;

import io.github.hlg212.fcf.model.BaseEnum;
import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum ValueTypeEnum implements BaseEnum {

    STATIC("STATIC","静态"),
    DYNAMIC("DYNAMIC","动态");

    private String value;
    private String desc;

    ValueTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
