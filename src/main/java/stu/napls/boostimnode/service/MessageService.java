package stu.napls.boostimnode.service;

import org.springframework.data.domain.Pageable;
import stu.napls.boostimnode.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> findByConversationUuidOrderByTimestamp(String conversationUuid, Pageable pageable);

    Message update(Message message);
}
