package io.github.hlg212.esb;

import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;


@CloudApplication
public class EsbApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(EsbApplication.class, args);
    }

}
