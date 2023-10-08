package top.kjwang.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.content.domain.entity.Notice;
import top.kjwang.share.content.service.NoticeService;

/**
 * @author kjwang
 * @date 2023/10/8 14:41
 * @description ShareController
 */

@RestController
@RequestMapping(value = "/share")
public class ShareController {
	@Resource
	private NoticeService noticeService;

	@GetMapping(value = "/notice")
	public CommonResp<Notice> getLatestNotice() {
		CommonResp<Notice> commonResp = new CommonResp<>();
		commonResp.setData(noticeService.getLatest());
		return commonResp;
	}
}
