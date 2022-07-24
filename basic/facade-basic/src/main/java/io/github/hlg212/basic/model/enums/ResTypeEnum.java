package io.github.hlg212.basic.model.enums;

import io.github.hlg212.fcf.model.BaseEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum ResTypeEnum implements BaseEnum {

    MENU("MENU","菜单"),
    COMPONENT("COMPONENT","组件"),
    INTERFACE("INTERFACE","接口"),
    DATA("DATA","数据权限");


    private String value;
    private String desc;

    ResTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
