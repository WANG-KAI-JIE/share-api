package top.kjwang.share.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.kjwang.share.common.resp.CommonResp;

/**
 * @author kjwang
 * @date 2023/10/12 09:26
 * @description UserService
 */

@FeignClient(value = "user-service", path = "/user")
public interface UserService {
	/**
	 * 调用用户中心查询用户信息接口
	 *
	 * @param id 用户id
	 * @return CommonResp<User>
	 */
	@GetMapping("/{id}")
	CommonResp<User> getUser(@PathVariable Long id);

	/**
	 * 调用用户中心修改用户积分接口
	 *
	 * @param userAddBonusMsgDTO 积分信息
	 * @return CommonResp<User>
	 */
	@PutMapping("/update-bonus")
	CommonResp<User> updateBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO);
}
