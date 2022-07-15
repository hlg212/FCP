package io.github.hlg212.cas;

import io.github.hlg212.fcf.annotation.CloudApplicationScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@CloudApplicationScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Application.class}))
public class AppAutoConfiguration //extends CloudApplicationAutoConfiguration
{
}
