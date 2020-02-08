package stu.napls.boostimnode.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimnode.auth.annotation.Auth;
import stu.napls.boostimnode.core.exception.Assert;
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.model.Conversation;
import stu.napls.boostimnode.service.ConversationService;
import stu.napls.boostimnode.service.MessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private MessageService messageService;

    @Resource
    private ConversationService conversationService;

    @ApiOperation("Get history of the conversation.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "size", value = "Size of a page"),
            @ApiImplicitParam(name = "page", value = "Page number starting from 0")
    })
    @Auth
    @GetMapping("/get/{conversationUuid}")
    private Response getByConversation(@PathVariable("conversationUuid") String conversationUuid, Pageable pageable, @ApiIgnore HttpSession session) {
        Conversation conversation = conversationService.findByUuid(conversationUuid);
        Assert.notNull(conversation, "Conversation does not exist.");
        Assert.isTrue(conversation.getUsers().contains(session.getAttribute("uuid").toString()),"Illegal authorization.");

        return Response.success(messageService.findByConversationUuidOrderByTimestamp(conversationUuid, pageable));
    }

}
