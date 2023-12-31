package top.kjwang.share.content.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.content.domain.dto.ShareAuditDTO;
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
@Tag(name = "知识分享管理员")
public class ShareAdminController {
	@Resource
	private ShareService shareService;

	@GetMapping(value = "/list")
	public CommonResp<List<Share>> getSharesNotYet() {
		CommonResp<List<Share>> commonResp = new CommonResp<>();
		commonResp.setData(shareService.querySharesNotYet());
		commonResp.setMessage("成功");
		return commonResp;
	}

	@PostMapping(value = "/audit/{id}")
	public CommonResp<Share> auditById(@PathVariable Long id, @RequestBody ShareAuditDTO auditDTO) {
		CommonResp<Share> commonResp = new CommonResp<>();
		commonResp.setData(shareService.auditById(id, auditDTO));
		commonResp.setMessage("成功");
		return commonResp;
	}
}
