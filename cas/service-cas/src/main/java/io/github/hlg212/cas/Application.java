package io.github.hlg212.cas;

import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;


@CloudApplication
public class Application  {
    public static void main(String args[])
    {
        SpringApplication.run(Application.class, args);
    }
}