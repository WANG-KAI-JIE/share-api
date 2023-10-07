package top.kjwang.share.user.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author kjwang
 * @date 2023/10/7 10:13
 * @description UserApplication
 */

@SpringBootApplication
@ComponentScan("top.kjwang")
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}