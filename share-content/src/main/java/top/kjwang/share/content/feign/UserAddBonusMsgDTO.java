package top.kjwang.share.content.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/12 12:11
 * @description UserAddBonusMsgDTO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddBonusMsgDTO {
	/**
	 * 为谁加积分
	 */
	private Long userId;

	/**
	 * 加多少分
	 */
	private Integer bonus;

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 积分事件（签到、投稿、兑换等）
	 */
	private String event;
}
