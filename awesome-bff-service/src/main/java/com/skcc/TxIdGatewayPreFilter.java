package com.skcc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Filter implementation for spring-cloud-gateway
 */
@Component
public class TxIdGatewayPreFilter implements GlobalFilter{
	
	private static final Logger log = LoggerFactory.getLogger(TxIdGatewayPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        UUID uuid = UUID.randomUUID();
		String txId = String.format("%s-%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), uuid.toString());
        
        exchange.getRequest()
            .mutate()
            .headers(h -> h.set("X-TXID", txId));
		log.info(String.format("gw/txid: %s (%s)", txId, exchange.getRequest().getURI()));

        return chain.filter(exchange);
    }
	
}
