package stu.napls.boostimnode.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import stu.napls.boostimnode.model.Node;
import stu.napls.boostimnode.model.User;
import stu.napls.boostimnode.service.NodeService;
import stu.napls.boostimnode.service.UserService;

import javax.annotation.Resource;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Resource
    private UserService userService;

    @Resource
    private NodeService nodeService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent sessionConnectedEvent) {

    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        User user = userService.findUserBySessionId(event.getSessionId());
        if (user != null) {
            // Release node
            Node node = user.getNode();
            if (node != null) {
                node.setClientNumber(node.getClientNumber() - 1);
                nodeService.update(node);
            }
            user.setNode(null);
            // Release session
            user.setSessionId(null);
            userService.update(user);
        }
    }

}
