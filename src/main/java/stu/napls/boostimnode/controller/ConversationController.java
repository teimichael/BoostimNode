package stu.napls.boostimnode.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.model.Conversation;
import stu.napls.boostimnode.model.User;
import stu.napls.boostimnode.service.ConversationService;
import stu.napls.boostimnode.service.UserService;
import stu.napls.boostimnode.util.ChatUtil;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @ApiOperation("Get conversation list of user.")
    @GetMapping("/get/list/{userUuid}")
    private Response getListByUser(@PathVariable("userUuid") String userUuid) {
        List<Conversation> conversations = conversationService.findByUserUuid(userUuid);
        return Response.success(conversations);
    }

    @ApiOperation("Clear unread records.")
    @PostMapping("/clear/unread")
    private Response clearUnread(@RequestParam String conversationUuid, @RequestParam String userUuid) {
        User user = userService.findUserByUuid(userUuid);
        user.setUnreadList(ChatUtil.getNewUnreadList(user.getUnreadList(), conversationUuid, ChatUtil.UNREAD_CLEAR));
        userService.update(user);
        return Response.success("Clear successfully.");
    }

}
