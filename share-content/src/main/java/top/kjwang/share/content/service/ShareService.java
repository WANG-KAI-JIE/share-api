package top.kjwang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import top.kjwang.share.common.resp.CommonResp;
import top.kjwang.share.content.domain.dto.ExchangeDTO;
import top.kjwang.share.content.domain.dto.ShareAuditDTO;
import top.kjwang.share.content.domain.dto.ShareRequestDTO;
import top.kjwang.share.content.domain.dto.UserAddBonusMQDTO;
import top.kjwang.share.content.domain.entity.MidUserShare;
import top.kjwang.share.content.domain.entity.Share;
import top.kjwang.share.content.enums.AuditStatusEnum;
import top.kjwang.share.content.feign.User;
import top.kjwang.share.content.feign.UserAddBonusMsgDTO;
import top.kjwang.share.content.feign.UserService;
import top.kjwang.share.content.mapper.MidUserShareMapper;
import top.kjwang.share.content.mapper.ShareMapper;
import top.kjwang.share.content.resp.ShareResp;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author kjwang
 * @date 2023/10/8 18:26
 * @description ShareService
 */

@Service
public class ShareService {
	@Resource
	private ShareMapper shareMapper;

	@Resource
	private MidUserShareMapper midUserShareMapper;

	@Resource
	private UserService userService;

	@Resource
	private RocketMQTemplate rocketTemplate;
	/**
	 * 查询某个用户首页可见的资源列表
	 *
	 * @param title 标题
	 * @param userId 用户id
	 */
	public List<Share> getList(String title, Integer pagaNo, Integer pageSize, Long userId) {
		// 构造查询条件
		LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
		// 按照 id 降序查询所有数据
		wrapper.orderByDesc(Share::getId);
		// 如标题关键字不空，则加上模糊查询条件，否则结果即为所有数据
		if (title != null) {
			wrapper.like(Share::getTitle,title);
		}
		// 过滤出所有已经通过审核的数据并需要显示的数据
		wrapper.eq(Share::getAuditStatus,"PASS").eq(Share::getShowFlag, true);

		// 内置的分页对象
		Page<Share> page = Page.of(pagaNo,pageSize);
		// 执行按条件查询
		List<Share> shares = shareMapper.selectList(page,wrapper);

		//处理后的 Share 数据列表
		List<Share> sharesDeal;
		// 1. 如果用户未登录，那么 downLoadUrl 全部设为 null
		if (userId == null){
			sharesDeal = shares.stream().peek(share -> share.setDownloadUrl(null)).collect(Collectors.toList());
		}
		// 2. 如果用户登录了，那么查询 mid_user_share，如果没有数据，那么这条 share 的 downLoadUrl 也设为 null
		// 只有自己分享的资源才能直接看到下载链接，否则显示"兑换"
		else {
			sharesDeal = shares.stream().peek(share -> {
				MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
						.eq(MidUserShare::getUserId,userId)
						.eq(MidUserShare::getShareId,share.getId()));
				if (midUserShare == null) {
					share.setDownloadUrl(null);
				}
			}).collect(Collectors.toList());
		}
		return sharesDeal;
	}
	public ShareResp findById(Long shareId) {
		Share share = shareMapper.selectById(shareId);
		CommonResp<User> commonResp = userService.getUser(share.getUserId());
		return ShareResp.builder().share(share).nickname(commonResp.getData().getNickname()).avatarUrl(commonResp.getData().getAvatarUrl()).build();
	}

	public Share exchange(ExchangeDTO exhchangeDTO) {
		Long userId = exhchangeDTO.getUserId();
		Long shareId = exhchangeDTO.getShareId();
		// 1. 根据 id 查询 share，校验需要兑换的资源是否存在
		Share share = shareMapper.selectById(shareId);
		if (share == null){
			throw new IllegalArgumentException("该分享不存在！");
		}

		// 2. 如果当前用户已经兑换过该分享，则直接返回该分享（不需要扣积分）
		MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
				.eq(MidUserShare::getUserId,userId)
				.eq(MidUserShare::getShareId,shareId));
		if (midUserShare != null) {
			return share;
		}

		// 3. 看用户积分是否足够
		CommonResp<User> commonResp = userService.getUser(userId);
		User user = commonResp.getData();
		// 兑换这个资源所需要的积分
		Integer price = share.getPrice();
		// 看积分是否够
		if (price > user.getBonus()) {
			throw new IllegalArgumentException("用户积分不够！");
		}

		// 4. 修改积分（*-1 就是负值，扣分）
		userService.updateBonus(UserAddBonusMsgDTO.builder().userId(userId).bonus(price * -1).build());
		// 5. 向 mid_user_share 表插入一条数据，让这个用户对于这条资源拥有了下载权限
		midUserShareMapper.insert(MidUserShare.builder().userId(userId).shareId(shareId).build());
		return share;
	}
	/**
	 * 投稿
	 * @param shareRequestDTO shareRequestDTO
	 * @return int
	 */
	public int contribute(ShareRequestDTO shareRequestDTO) {
		Share share = Share.builder()
				.isOriginal(shareRequestDTO.getIsOriginal())
				.author(shareRequestDTO.getAuthor())
				.price(shareRequestDTO.getPrice())
				.downloadUrl(shareRequestDTO.getDownloadUrl())
				.summary(shareRequestDTO.getSummary())
				.buyCount(0)
				.title(shareRequestDTO.getTitle())
				.userId(shareRequestDTO.getUserId())
				.cover(shareRequestDTO.getCover())
				.createTime(new Date())
				.updateTime(new Date())
				.showFlag(false)
				.auditStatus("NOT_YET")
				.reason("未审核")
				.build();
		return shareMapper.insert(share);
	}
	/**
	 * 我的投稿
	 *
	 * @param pageNo 页码
	 * @param pageSize 每页大小
	 * @param userId 用户id
	 * @return shares
	 */
	public List<Share> myContribute(Integer pageNo,Integer pageSize,Long userId) {
		LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(Share::getId);
		wrapper.eq(Share::getUserId,userId);
		Page<Share> page = Page.of(pageNo,pageSize);
		return shareMapper.selectList(page,wrapper);
	}

	/**
	 * 查询待审核状态的share列表
	 *
	 * @return List<Share>
	 */
	public List<Share> querySharesNotYet() {
		LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(Share::getId);
		wrapper.eq(Share::getShowFlag,false)
				.eq(Share::getAuditStatus,"NOT_YET");
		return shareMapper.selectList(wrapper);
	}

	/**
	 * 审核
	 *
	 * @param id id
	 * @param shareAduidtDTO shareAduidtDTO
	 * @return share
	 */
	public Share auditById(Long id, ShareAuditDTO shareAduidtDTO) {
		// 1. 查询 share 是否存在，不存在或者当前的 audit_status ！= NOT_YET，邦么拋异常
		Share share = shareMapper.selectById(id);
		if (share == null) {
			throw new IllegalArgumentException("参数非法！该分享不存在");
		}
		if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
			throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
		}
		// 2. 审核资源，将状态改为 PASS 或 REJECT，更新原因和是否发布显示
		share.setAuditStatus(shareAduidtDTO.getAuditStatusEnum().toString());
		share.setReason(shareAduidtDTO.getReason());
		share.setShowFlag(shareAduidtDTO.getShowFlag());
		LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Share::getId, id);
		this.shareMapper.update(share, wrapper);

		// 3. 向 mid_user 插入一条数据，分享的作者通过审核后，默认拥有了下载权限
		this.midUserShareMapper.insert(
				MidUserShare.builder()
						.userId(share.getUserId())
						.shareId(id)
						.build()
		);

		// 4. 如果是 PASS，那么发送消息给 rocketmq，让用户中心去消费，并为发布人添加积分（投稿加 50 积分）
		if (AuditStatusEnum.PASS.equals(shareAduidtDTO.getAuditStatusEnum())) {
			this.rocketTemplate.convertAndSend(
					"add-bonus",
					UserAddBonusMQDTO.builder()
							.userId(share.getUserId())
							.bonus(50)
							.build());
		}
		return share;
	}
}
