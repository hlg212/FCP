package io.github.hlg212.task.handler;

import io.github.hlg212.fcf.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author huangligui
 * @since 2022/7/23 16:03
 */
@Slf4j
@Component
public class HelloTestTask implements Task {

    @Override
    public String executeTask(String param) {
        log.info("HelloTestTask >>>>>>>>>>>>>>>>>>" + param);
        return null;
    }
}
