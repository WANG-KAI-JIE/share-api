package top.kjwang.share.content.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/8 18:18
 * @description MidUserShare
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidUserShare {
	private Long id;

	private Long shareId;

	private Long userId;
}
