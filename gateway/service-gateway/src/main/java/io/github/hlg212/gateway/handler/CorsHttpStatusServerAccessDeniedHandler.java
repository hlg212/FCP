package io.github.hlg212.gateway.handler;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class CorsHttpStatusServerAccessDeniedHandler extends HttpStatusServerAccessDeniedHandler {

    public CorsHttpStatusServerAccessDeniedHandler() {
        super(HttpStatus.FORBIDDEN);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap(response ->{
                HttpHeaders headers = response.getHeaders();
                String origin =  exchange.getRequest().getHeaders().getOrigin();
                if(StringUtils.isEmpty(origin))
                {
                    origin = "*";
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                return super.handle(exchange,e);
            });
    }
}
