package io.github.hlg212.esb.mq;

import io.github.hlg212.esb.conf.EventBusConfig;
import io.github.hlg212.esb.service.RabbitMqService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * mq exchange事件监听
 * 当属于esb 的 exchange创建时，根据规则绑定
 * @author: huangligui
 * @create: 2019-02-15 15:30
 **/
@Component
@Slf4j
public class ExchangeEventListener {

    private final static String ESB_PREFIX = "eventServiceBus";

    private final static String EXC_CREATED = "exchange.created";

    private final static String EXC_DELETED = "exchange.deleted";

    @Autowired
    private RabbitMqService rabbitMqService;

    @StreamListener(EventBusConfig.EventServiceBusEventClient.INPUT)
    public void acceptEventServiceBusCreate(
            @Header("name") String name,
            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String event,
            @Header(AmqpHeaders.CHANNEL) Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        log.info("{},{}",name,event);
        this.ack(name, event, channel, deliveryTag);
    }

    /**
     * ESB事件绑定与解绑
     *
     */
    private void exchangeBind(String name,String event) {
        String[] exchanges = StringUtils.split(name, ".");
        if(exchanges.length>1 && ESB_PREFIX.equals(exchanges[0])) {
            if(EXC_CREATED.equals(event)) {
                rabbitMqService.bindExchangeToExchange(ESB_PREFIX, name, exchanges[1]+".#");
            }
            if(EXC_DELETED.equals(event)) {
                rabbitMqService.unbindExchangeToExchange(ESB_PREFIX, name);
            }
        }
    }

    /**
     * 根据前缀绑定 绑定 exchange 并确认消费消息
     *
     */
    private void ack(String name,String event, Channel channel,Long deliveryTag) {
        boolean flag = true;
        try {
            this.exchangeBind(name,event);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e){
            flag = false;
            log.error(e.getMessage(),e);
        } finally {
            if( !flag ){
                try {
                    channel.basicNack(deliveryTag, false, true);
                }catch (Exception ee){
                    log.error(ee.getMessage(),ee);
                }
            }
        }
    }
}
