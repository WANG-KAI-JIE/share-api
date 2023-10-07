package top.kjwang.share.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/7 12:57
 * @description LoginDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
	private String phone;

	private String password;
}
