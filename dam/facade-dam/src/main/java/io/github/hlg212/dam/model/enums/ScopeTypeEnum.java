package io.github.hlg212.dam.model.enums;

import io.github.hlg212.fcf.model.BaseEnum;
import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum ScopeTypeEnum implements BaseEnum {

    ORG("ORG","机构"),
    USER_ORG("USER_ORG","用户机构"),
    DATE("DATE","时间"),
    NUMBER("NUMBER","数字"),
    NUMBER_INT("NUMBER_INT","数字-整型");

    private String value;
    private String desc;

    ScopeTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
