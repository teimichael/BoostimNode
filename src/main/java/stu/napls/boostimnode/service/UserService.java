package stu.napls.boostimnode.service;

import stu.napls.boostimnode.model.User;

public interface UserService {
    User findUserByUuid(String uuid);

    User findUserBySessionId(String sessionId);

    User update(User user);
}
