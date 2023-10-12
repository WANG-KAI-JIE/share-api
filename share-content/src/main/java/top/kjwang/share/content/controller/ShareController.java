package top.kjwang.share.content.controller;

import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.common.util.JwtUtil;
import top.kjwang.share.content.domain.dto.ExchangeDTO;
import top.kjwang.share.content.domain.entity.Notice;
import top.kjwang.share.content.domain.entity.Share;
import top.kjwang.share.content.resp.ShareResp;
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
@RefreshScope
public class ShareController {
	@Resource
	private NoticeService noticeService;

	@Resource
	private ShareService shareService;

	@Value("${show}")
	Boolean flag;

	// 定义每页最多的数据量，以防前端定义传递超大參数，造成页面数据量过大
	private final int MAX = 100;

	/**
	 * 封装一个从 token 中提取 userId 的方法
	 * @param token
	 * @return userId
	 */
	private long getUserIdFromToken(String token) {
		log.info(">>>>>>>>> token" + token);
		long userId = 0;
		String noToken = "no-token";
		if (!noToken.equals(token)) {
			JSONObject jsonObject = JwtUtil.getJSONObject(token);
			log.info("解析到 token 的 json 数据为：{}",jsonObject);
			userId = Long.parseLong(jsonObject.get("id").toString());
		} else {
			log.info("没有 token");
		}
		return userId;
	}

	@GetMapping(value = "/notice")
	public CommonResp<Notice> getLatestNotice() {
		CommonResp<Notice> commonResp = new CommonResp<>();
		Notice notice = noticeService.getLatest();
		notice.setShowFlag(flag);
		commonResp.setData(notice);
		return commonResp;
	}

	@GetMapping("/list")
	public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title,
												@RequestParam(required = false, defaultValue = "1") Integer pageNo,
												@RequestParam(required = false, defaultValue = "3") Integer pageSize,
												@RequestHeader(value = "token",  required = false) String token
												) {
		if (pageSize > MAX) {
			pageSize = MAX;
		}
		long userId = getUserIdFromToken(token);
		CommonResp<List<Share>> commonResp = new CommonResp<>();
		commonResp.setData(shareService.getList(title,pageNo,pageSize,userId));
		return commonResp;
	}

	@GetMapping("/{id}")
	public CommonResp<ShareResp> getShareById(@PathVariable Long id) {
		ShareResp shareResp = shareService.findById(id);
		CommonResp<ShareResp> commonResp = new CommonResp<>();
		commonResp.setData(shareResp);
		return commonResp;
	}

	@PostMapping("/exchange")
	public CommonResp<Share> exchange(@RequestBody ExchangeDTO exchangeDTO) {
		System.out.println(exchangeDTO);
		CommonResp<Share> commonResp = new CommonResp<>();
		commonResp.setData(shareService.exchange(exchangeDTO));
		return commonResp;
	}
}
