package top.kjwang.share.common.exception;

import lombok.Getter;

/**
 * @author kjwang
 * @date 2023/10/7 13:35
 * @description BusinessException
 */

@Getter
public class BusinessException extends RuntimeException{
	private BusinessExceptionEnum e;

	public BusinessException(BusinessExceptionEnum e) {
		this.e = e;
	}
	public void setE(BusinessExceptionEnum e) {
		this.e = e;
	}

	/**
	 * 不写入堆栈信息，提高性能
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
