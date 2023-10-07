package top.kjwang.share.user.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.kjwang.share.user.mapper.UserMapper;

/**
 * @author kjwang
 * @date 2023/10/7 12:40
 * @description UserService
 */

@Service
public class UserService {
	@Resource
	private UserMapper userMapper;

	public Long count() {
		return userMapper.selectCount(null);
	}
}
