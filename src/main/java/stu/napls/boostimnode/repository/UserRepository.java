package stu.napls.boostimnode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostimnode.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUuid(String uuid);

    User findBySessionId(String sessionId);
}

