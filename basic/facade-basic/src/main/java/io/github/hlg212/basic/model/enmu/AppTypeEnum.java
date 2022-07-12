package io.github.hlg212.basic.model.enmu;

import lombok.Data;
import lombok.Getter;

/**
 * 应用类型
 *
 * @author: huangligui
 * @date: 2022/7/3 20:44
 */
@Getter
public enum AppTypeEnum implements BaseEnum {

   WEB("WEB","前端web"),
    SERVICE("SERVICE","后端service");

    private String value;
    private String desc;

    AppTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
