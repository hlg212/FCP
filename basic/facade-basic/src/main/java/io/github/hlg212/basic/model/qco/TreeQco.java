package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/**
 * @author: Administrator
 * @date: 2022/5/24 20:02
 */
@Data
public class TreeQco extends Qco {

    @Field(description="级别")
    private Integer treeLevelGt;

    @Field(description="定位码右模糊匹配")
    private String locationCodeRtLike;

    @Field(description="名称模糊匹配")
    private String nameLike;

    @Field(description="编码模糊匹配")
    private String codeLike;

}
