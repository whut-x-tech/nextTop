package top.liuqiao.nextTop.exception;

import lombok.Getter;
import top.liuqiao.nextTop.common.ErrorCode;


/**
 * 自定义异常类
 *
 whut-online-judge
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}

