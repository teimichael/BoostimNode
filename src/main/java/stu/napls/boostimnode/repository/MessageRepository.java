package stu.napls.boostimnode.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostimnode.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationUuidOrderByTimestamp(String conversationId, Pageable pageable);

}

