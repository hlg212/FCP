package io.github.hlg212.generator.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.List;

@Data
public class ProjectGroupGeneratorBo {
    // maven
    @Field(description = "maven包路径(group)")
    private String mavenPackage;

    @Field(description = "框架版本,不填默认最新")
    private String frameVersion;

    @Field(description = "maven(artifactId)")
    private String groupCode;
    @Field(description = "版本")
    private String version;

    @Field(description = "maven父级包路径(group)")
    private String parentMavenPackage="io.github.hlg212";
    @Field(description = "maven父级包版本")
    private String parentVersion="2.0.0-SNAPSHOT";
    @Field(description = "maven父级代码(artifactId)")
    private String parentCode="parent";

    private List<ProjectGeneratorBo> projects;


}
