package stu.napls.boostimnode.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimnode.auth.annotation.Auth;
import stu.napls.boostimnode.auth.model.*;
import stu.napls.boostimnode.auth.request.AuthRequest;
import stu.napls.boostimnode.core.dictionary.ResponseCode;
import stu.napls.boostimnode.core.dictionary.StatusCode;
import stu.napls.boostimnode.core.exception.Assert;
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.model.User;
import stu.napls.boostimnode.model.vo.LoginVO;
import stu.napls.boostimnode.model.vo.SocketThirdRegister;
import stu.napls.boostimnode.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/access")
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    @Resource
    private AuthRequest authRequest;

    @Resource
    private UserService userService;

    @PostMapping("/third/register")
    @ResponseBody
    public Response registerFromThird(@RequestBody SocketThirdRegister socketThirdRegister) {
        Assert.isTrue(userService.findUserByUuid(socketThirdRegister.getUuid()) == null, "User has been registered");
        User user = new User();
        user.setUuid(socketThirdRegister.getUuid());
        user.setStatus(StatusCode.NORMAL);
        userService.update(user);
        return Response.success("Register successfully");
    }

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody LoginVO loginVO) {
        // Obtain token
        AuthResponse authResponse = authRequest.login(loginVO.getAuthLogin());
        Assert.notNull(authResponse, "Authentication failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, authResponse.getMessage());

        // Obtain UUID
        AuthVerify authVerify = new AuthVerify();
        authVerify.setToken(authResponse.getData().toString());
        authResponse = authRequest.verify(authVerify);
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, authResponse.getMessage());

        User user = userService.findUserByUuid(authResponse.getData().toString());
        Assert.notNull(user, HttpStatus.UNAUTHORIZED.value(), "User does not exist.");

        // Update session
        user.setSessionId(loginVO.getSessionId());
        userService.update(user);

        return Response.success("Login successfully.", user);
    }

    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestParam String username, @RequestParam String password) {
        AuthPreregister authPreregister = new AuthPreregister();
        authPreregister.setUsername(username);
        authPreregister.setPassword(password);
        AuthResponse authResponse = authRequest.preregister(authPreregister);
        Assert.notNull(authResponse, "Preregistering auth server failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, authResponse.getMessage());
        String uuid = authResponse.getData().toString();

        User user = new User();
        user.setUuid(uuid);
        user.setStatus(StatusCode.NORMAL);
        userService.update(user);

        AuthRegister authRegister = new AuthRegister();
        authRegister.setUuid(uuid);
        authResponse = authRequest.register(authRegister);
        Assert.isTrue(authResponse != null && authResponse.getCode() == ResponseCode.SUCCESS, "Register failed. Please contact the administrator.");

        return Response.success("Register successfully");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/logout")
    @ResponseBody
    public Response logout(@ApiIgnore HttpSession session) {
        AuthLogout authLogout = new AuthLogout();
        authLogout.setUuid(session.getAttribute("uuid").toString());
        AuthResponse authResponse = authRequest.logout(authLogout);
        Assert.notNull(authResponse, "Authentication failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, "Logout failed.");
        return Response.success("Logout successfully.");
    }
}
