package io.github.hlg212.gateway.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(havingValue = "true",value = "enabled",prefix = "fcf.gateway.security")
@Inherited
public @interface SecurityConditional {
}
