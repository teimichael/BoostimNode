package stu.napls.boostimnode.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostimnode.model.User;
import stu.napls.boostimnode.repository.UserRepository;
import stu.napls.boostimnode.service.UserService;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public User findUserBySessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
