package stu.napls.boostimnode.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimnode.auth.annotation.Auth;
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.model.Conversation;
import stu.napls.boostimnode.model.User;
import stu.napls.boostimnode.service.ConversationService;
import stu.napls.boostimnode.service.UserService;
import stu.napls.boostimnode.util.ChatUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @ApiOperation("Get conversation list of user.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @GetMapping("/get/list/{userUuid}")
    private Response getListByUser(@PathVariable("userUuid") String userUuid, @ApiIgnore HttpSession session) {
        List<Conversation> conversations = conversationService.findByUserUuid(userUuid);
        return Response.success(conversations);
    }

    @ApiOperation("Clear unread records.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/clear/unread")
    private Response clearUnread(@RequestParam String conversationUuid, @RequestParam String userUuid,@ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(userUuid);
        user.setUnreadList(ChatUtil.getNewUnreadList(user.getUnreadList(), conversationUuid, ChatUtil.UNREAD_CLEAR));
        userService.update(user);
        return Response.success("Clear successfully.");
    }

}
