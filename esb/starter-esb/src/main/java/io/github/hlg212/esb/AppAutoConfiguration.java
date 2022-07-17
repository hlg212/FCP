package io.github.hlg212.esb;

import io.github.hlg212.fcf.annotation.CloudApplicationScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@CloudApplicationScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {EsbApplication.class}))
public class AppAutoConfiguration
{
}
