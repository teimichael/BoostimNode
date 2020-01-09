package stu.napls.boostimnode.model.vo;

import lombok.Data;
import stu.napls.boostimnode.auth.model.AuthLogin;

@Data
public class LoginVO {

    private String sessionId;

    private AuthLogin authLogin;
}
