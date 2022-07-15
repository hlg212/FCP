package io.github.hlg212.gateway;

import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;


@CloudApplication
public class GatewayApplication {

    public static void main(String args[])
    {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
