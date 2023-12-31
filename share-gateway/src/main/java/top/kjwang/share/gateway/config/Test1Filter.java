//package top.kjwang.share.gateway.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @author kjwang
// * @date 2023/10/7
// * @description Test1Filter
// **/
//@Component
//@Slf4j
//public class Test1Filter implements GlobalFilter, Ordered {
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		log.info("Test1Filter");
//		return chain.filter(exchange);
//	}
//
//	@Override
//	public int getOrder() {
//		return 0;
//	}
//}
