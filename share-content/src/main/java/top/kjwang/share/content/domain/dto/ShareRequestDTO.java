package top.kjwang.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kjwang
 * @date 2023/10/13 13:50
 * @description ShareRequestDTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareRequestDTO {
	private Long userId;

	private String author;

	private String title;

	private Boolean isOriginal;

	private Integer price;

	private String downloadUrl;

	private String cover;

	private String summary;
}
