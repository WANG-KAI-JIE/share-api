package top.kjwang.share.content.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kjwang.share.content.domain.entity.Share;

/**
 * @author kjwang
 * @date 2023/10/12 09:35
 * @description Shareresp
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareResp {
	/**
	 * 分享内容对象
	 */
	private Share share;

	/**
	 * 发布者昵称
	 */
	private String nickname;

	/**
	 * 发布者头像
	 */
	private String avatarUrl;
}
