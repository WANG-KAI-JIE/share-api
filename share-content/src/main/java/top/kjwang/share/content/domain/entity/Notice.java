package top.kjwang.share.content.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kjwang
 * @date 2023/10/8 14:32
 * @description Notice
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Notice {
	private Long id;

	private String content;

	private Boolean showFlag;

	@JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
