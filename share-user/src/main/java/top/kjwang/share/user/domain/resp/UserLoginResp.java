package top.kjwang.share.user.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kjwang.share.user.domain.entity.User;

/**
 * @author kjwang
 * @date 2023/10/7 14:17
 * @description UserLoginResp
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResp {
	private User user;

	private String token;
}
