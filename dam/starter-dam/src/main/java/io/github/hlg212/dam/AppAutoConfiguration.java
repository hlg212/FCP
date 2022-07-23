package io.github.hlg212.dam;

import io.github.hlg212.fcf.annotation.CloudApplicationScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@CloudApplicationScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DamApplication.class}))
public class AppAutoConfiguration
{
}
