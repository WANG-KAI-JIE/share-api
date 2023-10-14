package top.kjwang.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/13 23:42
 * @description UserAddBonusMQDTO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddBonusMQDTO {
	private Long userId;
	private Integer bonus;
}
