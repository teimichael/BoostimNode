package stu.napls.boostimnode.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.boostimnode.core.dictionary.APIConst;
import stu.napls.boostimnode.core.dictionary.ResponseCode;
import stu.napls.boostimnode.core.response.Response;
import stu.napls.boostimnode.model.Message;
import stu.napls.boostimnode.util.SimpMessageHeaderAccessorFactory;

import javax.annotation.Resource;

@RestController
public class ChatController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(APIConst.SEND_PRIVATE_MESSAGE + "/{sessionId}")
    public int sendPrivateMessage(@PathVariable("sessionId") String sessionId, @RequestBody Message message) {
        simpMessagingTemplate.convertAndSendToUser(sessionId, APIConst.PRIVATE_CHANNEL, Response.success(message), SimpMessageHeaderAccessorFactory.getMessageHeaders(sessionId));
        return ResponseCode.SUCCESS;
    }
}
