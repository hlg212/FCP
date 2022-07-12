package io.github.hlg212.system.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

/**
 * @author: Administrator
 * @date: 2022/7/10 13:07
 */
@Data
public class CodeLoginBo {

    @Field(description="授权码,必填")
    private String code;
    @Field(description="申请授权码时的跳转地址,必填")
    private String redirectUri;
    @Field(description="应用代码")
    private String appCode;
}
