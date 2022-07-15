package io.github.hlg212.system;

import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;

@CloudApplication
public class SystemApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(SystemApplication.class, args);
    }



}
