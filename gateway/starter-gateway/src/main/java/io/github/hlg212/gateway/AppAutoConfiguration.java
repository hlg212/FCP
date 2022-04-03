package io.github.hlg212.gateway;

import io.github.hlg212.fcf.annotation.CloudApplicationScan;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;

@CloudApplicationScan
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Application.class}))
public class AppAutoConfiguration //extends CloudApplicationAutoConfiguration
{
}
