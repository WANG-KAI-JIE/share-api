package top.kjwang.share.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kjwang.share.common.exception.BusinessException;
import top.kjwang.share.common.resp.CommonResp;

/**
 * @author kjwang
 * @date 2023/10/7 13:25
 * @description ControllerExceptionHandler
 */

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
	/**
	 * 系统异常统一处理
	 * @param e Exception
	 * @return CommonResp
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public CommonResp<?> exceptionHandler(Exception e) throws Exception {
		CommonResp<?> resp = new CommonResp<>();
		log.error("系统异常",e);
		resp.setSuccess(false);
		resp.setMessage(e.getMessage());
		return resp;
	}

	/**
	 * 业务异常统一处理
	 * @param e Exception
	 * @return CommonResp
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public CommonResp<?> exceptionHandler(BusinessException e) {
		CommonResp<?> commonResp = new CommonResp<>();
		log.error("业务异常",e);
		commonResp.setSuccess(false);
		commonResp.setMessage(e.getE().getDesc());
		return commonResp;
	}
}
