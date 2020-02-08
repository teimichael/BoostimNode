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
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.service.MessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private MessageService messageService;

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
        return Response.success(messageService.findByConversationUuidOrderByTimestamp(conversationUuid, pageable));
    }

}
