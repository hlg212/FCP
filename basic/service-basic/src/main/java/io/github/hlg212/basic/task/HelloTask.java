package io.github.hlg212.basic.task;

import io.github.hlg212.fcf.Task;
import io.github.hlg212.fcf.annotation.TaskRegister;

/**
 * @author: Administrator
 * @date: 2022/4/6 10:18
 */
@TaskRegister(cron = "0/3 * * * * ? ",name = "打印Hello的任务")
public class HelloTask implements Task {

    @Override
    public String executeTask(String param) {
        System.out.println("每三秒打印一次Hello");
        return null;

    }
}
