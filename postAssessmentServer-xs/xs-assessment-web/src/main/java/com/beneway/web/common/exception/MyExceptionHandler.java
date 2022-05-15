package com.beneway.web.common.exception;

import com.beneway.common.common.result.Result;
import com.beneway.common.common.result.ResultCodeEnum;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.exception.TokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2020/3/12
 * @time: 9:50
 */
@Log4j2
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public Result handleRRException(RRException e){
        Result error = Result.error(ResultCodeEnum.ERROR_BUSINESS, e.getMsg());
        setExceptionInfo(error, e);
        return error;
    }

    /**
     * 处理自定义token异常
     */
    @ExceptionHandler(TokenException.class)
    public Result handleTokenException(TokenException e){
        Result error = Result.error(ResultCodeEnum.ERROR_TOKEN, e.getMsg());
        setExceptionInfo(error, e);
        return error;
    }

    /**
     * 数据库唯一索引错误
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        Result error = Result.error(ResultCodeEnum.ERROR_BUSINESS, "sql重复键异常");
        setExceptionInfo(error, e);
        return error;
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        Result error = Result.error(ResultCodeEnum.ERROR_SERVER);
        setExceptionInfo(error, e);
        return error;
    }

    private void setExceptionInfo(Result result, Throwable e){
        MyExceptionInfo.setExceptionInfo(result.getCode().getCode(), result.getMsg(), e);
    }

}
