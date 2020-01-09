package stu.napls.boostimnode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import stu.napls.boostimnode.service.ConversationService;
import stu.napls.boostimnode.service.UserService;

import javax.annotation.Resource;

@SpringBootTest
class BoostimNodeApplicationTests {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {

    }

}
