package top.kjwang.share.content.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author kjwang
 * @date 2023/10/7 10:13
 * @description UserApplication
 */

@SpringBootApplication
@ComponentScan("top.kjwang")
@Slf4j
@MapperScan("top.kjwang.share.*.mapper")
@EnableFeignClients(basePackages = {"top.kjwang"})
public class ContentApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ContentApplication.class);
		Environment env = app.run(args).getEnvironment();
		log.info("内容服务启动成功！！！");
		log.info("测试地址：http://127.0.0.1:{}{}",env.getProperty("server.port"),env.getProperty("server.servlet.context-path"));
	}
}