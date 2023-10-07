package top.kjwang.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
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
	public Long count() {
		return userService.count();
	}

	@PostMapping("/login")
	public User login(@RequestBody LoginDTO loginDTO){
		return userService.login(loginDTO);
	}
}
