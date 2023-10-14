package top.kjwang.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kjwang.share.content.enums.AuditStatusEnum;

/**
 * @author kjwang
 * @date 2023/10/13 21:50
 * @description ShareAuditDTO 审核数据传输对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareAuditDTO {
	/**
	 * 审核状态：枚举
	 */
	private AuditStatusEnum auditStatusEnum;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 是否发布展示
	 */
	private Boolean showFlag;
}
