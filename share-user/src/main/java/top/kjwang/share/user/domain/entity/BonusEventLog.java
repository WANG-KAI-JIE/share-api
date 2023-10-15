package top.kjwang.share.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author kjwang
 * @date 2023/10/12 12:08
 * @description BonusEventLog
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusEventLog {

	private Long id;

	private Long userId;

	private Integer value;

	private String description;

	private String event;

	@JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
