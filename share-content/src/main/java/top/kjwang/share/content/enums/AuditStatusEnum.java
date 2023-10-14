package top.kjwang.share.content.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kjwang
 * @date 2023/10/13 21:46
 * @description AuditStatusEnum 审核状态枚举
 */

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
	/**
	 * 待审核
	 */
	NOT_YET,
	/**
	 * 审核通过
	 */
	PASS,
	/**
	 * 审核不通过
	 */
	REJECT
}
