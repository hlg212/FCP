package io.github.hlg212.task;

import io.github.hlg212.fcf.annotation.CloudApplication;

import org.springframework.boot.SpringApplication;

@CloudApplication
public class TaskApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(TaskApplication.class, args);
    }

}
