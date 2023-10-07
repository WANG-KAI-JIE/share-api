package top.kjwang.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.user.domain.dto.LoginDTO;
import top.kjwang.share.user.domain.entity.User;
import top.kjwang.share.user.service.UserService;

/**
 * @author kjwang
 * @date 2023/10/7 12:44
 * @description UserController
 */

@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@GetMapping("/count")
	public CommonResp<Long> count() {
		Long count = userService.count();
		CommonResp<Long> commonResp = new CommonResp<>();
		commonResp.setData(count);
		return commonResp;
	}

	@PostMapping("/login")
	public CommonResp<User> login(@Valid LoginDTO loginDTO) {
		User user = userService.login(loginDTO);
		CommonResp<User> commonResp = new CommonResp<>();
		commonResp.setData(user);
		return commonResp;
	}

	@PostMapping("/register")
	public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
		Long id = userService.register(loginDTO);
		CommonResp<Long> commonResp = new CommonResp<>();
		commonResp.setData(id);
		return commonResp;
	}
}
