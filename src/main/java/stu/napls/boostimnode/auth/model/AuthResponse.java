package stu.napls.boostimnode.auth.model;

import lombok.Data;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Data
public class AuthResponse {

    private int code;
    private String message;
    private Object data;

}
