package top.liuqiao.nextTop.service;

import org.springframework.stereotype.Service;
import top.liuqiao.nextTop.model.request.UserLoginRequest;
import top.liuqiao.nextTop.model.request.UserRegisterRequest;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
public interface UserService {
    Long login(UserLoginRequest userLoginRequest);

    Boolean register(UserRegisterRequest userRegisterRequest);
}
