package top.kjwang.share.user.controller;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.common.util.JwtUtil;
import top.kjwang.share.user.domain.dto.LoginDTO;
import top.kjwang.share.user.domain.dto.UserAddBonusMsgDTO;
import top.kjwang.share.user.domain.entity.BonusEventLog;
import top.kjwang.share.user.domain.entity.User;
import top.kjwang.share.user.domain.resp.UserLoginResp;
import top.kjwang.share.user.service.UserService;

import java.util.List;

/**
 * @author kjwang
 * @date 2023/10/7 12:44
 * @description UserController
 */

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "user参数")
public class UserController {
	@Resource
	private UserService userService;

	/**
	 * 封装一个从 token 中提取 userId 的方法
	 * @param token
	 * @return userId
	 */
	private long getUserIdFromToken(String token) {
		log.info(">>>>>>>>> token" + token);
		long userId = 0;
		String noToken = "no-token";
		if (!noToken.equals(token)) {
			JSONObject jsonObject = JwtUtil.getJSONObject(token);
			log.info("解析到 token 的 json 数据为：{}",jsonObject);
			userId = Long.parseLong(jsonObject.get("id").toString());
		} else {
			log.info("没有 token");
		}
		return userId;
	}

	@GetMapping("/count")
	public CommonResp<Long> count() {
		Long count = userService.count();
		CommonResp<Long> commonResp = new CommonResp<>();
		commonResp.setData(count);
		return commonResp;
	}

	@Operation(summary = "登录")
	@Parameters(@Parameter(name = "loginDTO",description = "登录参数"))
	@PostMapping("/login")
	public CommonResp<UserLoginResp> login(@Valid @RequestBody LoginDTO loginDTO) {
		UserLoginResp userLoginResp = userService.login(loginDTO);
		CommonResp<UserLoginResp> commonResp = new CommonResp<>();
		commonResp.setData(userLoginResp);
		return commonResp;
	}

	@PostMapping("/register")
	public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
		Long id = userService.register(loginDTO);
		CommonResp<Long> commonResp = new CommonResp<>();
		commonResp.setData(id);
		return commonResp;
	}

	@GetMapping("/{id}")
	public CommonResp<User> getUserById(@PathVariable Long id) {
		User user = userService.findById(id);
		CommonResp<User> commonResp = new CommonResp<>();
		commonResp.setData(user);
		return commonResp;
	}

	@PutMapping(value = "/update-bonus")
	public CommonResp<User> updateBonuses(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
		Long userId = userAddBonusMsgDTO.getUserId();
		userService.updateBonuses(
				UserAddBonusMsgDTO.builder()
						.userId(userId)
						.bonus(userAddBonusMsgDTO.getBonus())
						.description("兑换分享")
						.event("BUY")
						.build()
		);
		CommonResp<User> commonResp = new CommonResp<>();
		commonResp.setData(userService.findById(userId));
		return commonResp;
	}


	/**
	 * 获取积分明细
	 *
	 */
	@GetMapping("/myValue")
	public CommonResp<List<BonusEventLog>> myValue(@RequestHeader(value = "token",required = false)String token) {
		long userId = getUserIdFromToken(token);
		CommonResp<List<BonusEventLog>> commonResp = new CommonResp<>();
		commonResp.setData(userService.myValue(userId));
		return commonResp;
	}
}
