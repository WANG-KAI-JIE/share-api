package top.kjwang.share.user.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.kjwang.share.common.interceptor.Loginterceptor;

/**
 * @author kjwang
 * @date 2023/10/8 13:54
 * @description SpringMvcConfig
 */

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
	@Resource
	private Loginterceptor loginterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginterceptor);
	}
}
