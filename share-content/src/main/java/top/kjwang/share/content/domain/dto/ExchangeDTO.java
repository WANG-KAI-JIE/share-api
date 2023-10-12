package top.kjwang.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/12 12:37
 * @description ExchangeDTO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDTO {
	private Long userId;

	private Long shareId;
}
