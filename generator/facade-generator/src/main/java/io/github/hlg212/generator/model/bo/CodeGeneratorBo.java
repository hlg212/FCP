package io.github.hlg212.generator.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

@Data

public class CodeGeneratorBo {

    @Field(description = "数据库信息")
    private DbBo db;

    @Field(description = "作者")
    private String author;
    @Field(description = "代码目录")
    private String codePackage;

    @Field(description = "表前缀")
    private String tablePrefix = "T_";

}
