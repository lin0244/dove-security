package com.licc.dove.web.exception;

import com.licc.dove.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lichangchao
 * @Description:
 * @date 2017/12/28
 */
@ControllerAdvice
public class ExceptionHandle {

  private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseVo handleException(Exception e) {
    try {
      if (e instanceof BindException) {
        BindingResult bindingResult = ((BindException) e).getBindingResult();
        if (bindingResult.hasErrors()) {
          return ResultUtil
              .error(ConstException.VALID_ERROR.getCode().toString(),
                  bindingResult.getFieldError().getDefaultMessage(), null);
        } else {
          throw new RuntimeException("bindingResult is null");
        }
      }
    } catch (Exception e1) {
      throw new RuntimeException(e);
    }
    if (e instanceof BusinessException) {
      return ResultUtil
          .error(ConstException.BIZ_ERROR.getCode().toString(), ConstException.BIZ_ERROR.getMsg(),
              null);
    } else {
      logger.error("【系统异常】{}", e);
      return ResultUtil
          .error(ConstException.UNKONW_ERROR.getCode().toString(),
              ConstException.UNKONW_ERROR.getMsg(), null);
    }
  }
}
