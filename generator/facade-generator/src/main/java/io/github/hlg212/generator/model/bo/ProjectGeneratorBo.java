package io.github.hlg212.generator.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

@Data
public class ProjectGeneratorBo {
    private CodeGeneratorBo codeGeneratorBo;

    // 代码包路径
    @Field(description = "包路径")
    private String codePackage;

    // maven
    @Field(description = "maven包路径(group)")
    private String mavenPackage;

    @Field(description = "框架版本,不填默认最新")
    private String frameVersion;

    @Field(description = "项目名称")
    private String projectName;

    @Field(description = "项目代码,appCode")
    private String projectCode;

    @Field(description = "maven父级包路径(group)")
    private String parentMavenPackage;
    @Field(description = "maven父级包版本")
    private String parentVersion;
    @Field(description = "maven父级代码(artifactId)")
    private String parentCode;

}
