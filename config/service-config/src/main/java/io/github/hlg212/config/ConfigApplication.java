package io.github.hlg212.config;

import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;


@CloudApplication
public class ConfigApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
