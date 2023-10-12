package top.kjwang.share.content.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kjwang
 * @date 2023/10/12 09:25
 * @description User
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private Long id;

	private String phone;

	private String password;

	private String nickname;

	private String roles;

	private String avatarUrl;

	private Integer bonus;

	private Date createTime;

	private Date updateTime;
}