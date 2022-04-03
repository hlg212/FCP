package io.github.hlg212.gateway.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(value = "globalEnabled",prefix = "htcf.gateway.security.blackWhiteList")
@Inherited
public @interface GlobalBlackWhiteListConditional {
}
