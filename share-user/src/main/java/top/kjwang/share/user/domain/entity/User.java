package top.kjwang.share.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kjwang
 * @date 2023/10/7 12:36
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

	@JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private Date updateTime;
}