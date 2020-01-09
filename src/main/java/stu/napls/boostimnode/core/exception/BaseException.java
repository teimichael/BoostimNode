package stu.napls.boostimnode.core.exception;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
