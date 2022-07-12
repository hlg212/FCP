package io.github.hlg212.basic.model.enmu;

import lombok.Getter;

/**
 * @author: Administrator
 * @date: 2022/7/3 21:10
 */
@Getter
public enum ResCategoryEnum implements BaseEnum {

    MENU("MENU","菜单"),
    IFACE("IFACE","接口"),
    DATA("DATA","数据权限");


    private String value;
    private String desc;

    ResCategoryEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
