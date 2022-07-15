package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


@Slf4j
public class TokenMessageListener implements MessageListener {

    private final static String accessKeyPrefix = "access";

    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] messageBody = message.getBody();
        String body = new String(messageBody);
        if (body.startsWith( accessKeyPrefix )) {
            int beginIndex = body.lastIndexOf(":") + 1;
            int endIndex = body.length();
            String token = body.substring(beginIndex, endIndex);
            log.info("expire token {}",token);
            onlineUserService.expiresToken(token);
        }
    }


}
