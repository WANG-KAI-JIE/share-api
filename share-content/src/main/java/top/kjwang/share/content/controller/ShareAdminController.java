package top.kjwang.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.content.domain.entity.Share;
import top.kjwang.share.content.service.ShareService;

import java.util.List;

/**
 * @author kjwang
 * @date 2023/10/13 15:28
 * @description ShareAdminController
 */

@RestController
@RequestMapping("/share/admin")
public class ShareAdminController {
	@Resource
	private ShareService shareService;

	@GetMapping(value = "/list")
	public CommonResp<List<Share>> getSharesNotYet() {
		CommonResp<List<Share>> commonResp = new CommonResp<>();
		commonResp.setData(shareService.querySharesNotYet());
		return commonResp;
	}
}
