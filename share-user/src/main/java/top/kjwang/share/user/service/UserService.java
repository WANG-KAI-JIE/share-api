package top.kjwang.share.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.kjwang.share.common.exception.BusinessException;
import top.kjwang.share.common.exception.BusinessExceptionEnum;
import top.kjwang.share.common.util.SnowUtil;
import top.kjwang.share.user.domain.dto.LoginDTO;
import top.kjwang.share.user.domain.entity.User;
import top.kjwang.share.user.domain.resp.UserLoginResp;
import top.kjwang.share.user.mapper.UserMapper;

import java.util.Date;
import java.util.Map;

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

	public UserLoginResp login(LoginDTO loginDTO) {
		// 根据手机号查询用户
		User userDB = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
		// 没找到，抛出运行时异常
		if (userDB == null) {
			throw new BusinessException(BusinessExceptionEnum.PHONE_NOT_EXIST);
		}
		// 密码错误
		if (!userDB.getPassword().equals(loginDTO.getPassword())) {
			throw new BusinessException(BusinessExceptionEnum.PASSWORD_ERROR);
		}
		// 都正确，返回
		UserLoginResp userLoginResp = UserLoginResp.builder()
				.user(userDB)
				.build();
		String key = "fatcat";
		Map<String, Object> map = BeanUtil.beanToMap(userLoginResp);
		String token = JWTUtil.createToken(map,key.getBytes());
		userLoginResp.setToken(token);
		return userLoginResp;
	}

	public Long register(LoginDTO loginDTO) {
		// 根据手机号查询用户
		User userDB = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
		// 找到了，手机号已被注册
		if (userDB != null) {
			throw new BusinessException(BusinessExceptionEnum.PHONE_EXIST);
		}
		User savedUser = User.builder()
				// 使用雪花算法生成 id
				.id(SnowUtil.getSnowflakeNextId())
				.phone(loginDTO.getPhone())
				.password(loginDTO.getPassword())
				.nickname("新用户")
				.roles("user")
				.avatarUrl("https://fatcat666.oss-cn-nanjing.aliyuncs.com/image/202302250942314.png")
				.bonus(100)
				.createTime(new Date())
				.updateTime(new Date())
				.build();
		userMapper.insert(savedUser);
		return savedUser.getId();
	}
}
