package io.github.hlg212.sbAdmin;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.github.hlg212.fcf.annotation.CloudApplication;
import org.springframework.boot.SpringApplication;

@CloudApplication
@EnableAdminServer
public class SbAdminApplication {

    public static void main(String args[])
    {
        SpringApplication.run(SbAdminApplication.class, args);
    }
}
