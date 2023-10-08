package top.kjwang.share.content.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.content.domain.entity.Notice;
import top.kjwang.share.content.domain.entity.Share;
import top.kjwang.share.content.service.NoticeService;
import top.kjwang.share.content.service.ShareService;

import java.util.List;

/**
 * @author kjwang
 * @date 2023/10/8 14:41
 * @description ShareController
 */

@RestController
@RequestMapping(value = "/share")
@Slf4j
public class ShareController {
	@Resource
	private NoticeService noticeService;

	@Resource
	private ShareService shareService;

	@GetMapping(value = "/notice")
	public CommonResp<Notice> getLatestNotice() {
		CommonResp<Notice> commonResp = new CommonResp<>();
		commonResp.setData(noticeService.getLatest());
		return commonResp;
	}

	@GetMapping("/list")
	public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title) {
		CommonResp<List<Share>> commonResp = new CommonResp<>();
		Long userId = 2L;
		commonResp.setData(shareService.getList(title,userId));
		return commonResp;
	}
}
