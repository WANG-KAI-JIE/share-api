package top.kjwang.share.common.exception;

import lombok.Getter;

/**
 * @author kjwang
 * @date 2023/10/7 13:32
 * @description BusinessExceptionEnum
 */

@Getter
public enum BusinessExceptionEnum {
	PHONE_NOT_EXIST("手机号不存在"),PASSWORD_ERROR("密码错误");

	private String desc;

	BusinessExceptionEnum(String desc) {
		this.desc = desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "BusinessExceptionEnum{" +
				"desc='" + desc + '\'' +
				'}';
	}
}
