package io.github.hlg212.esb.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.SubscribableChannel;

/**
 * exchange 绑定与解绑
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-01-18 13:28
 **/
@EnableBinding({EventBusConfig.EventServiceBusEventClient.class})
@Configuration("esb.EventBusConfig")
public class EventBusConfig {

    public interface  EventServiceBusEventClient
    {
        public final static String INPUT = "eventServiceBusBindInput";
        @Input(INPUT)
        SubscribableChannel eventServiceBusBindInput();
    }

}
