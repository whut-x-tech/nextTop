package top.liuqiao.nextTop.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liuqiao.nextTop.common.BaseResponse;
import top.liuqiao.nextTop.common.ResultUtils;
import top.liuqiao.nextTop.model.request.UserLoginRequest;
import top.liuqiao.nextTop.model.request.UserRegisterRequest;
import top.liuqiao.nextTop.service.UserService;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<Long> login(@RequestBody @Validated UserLoginRequest userLoginRequest) {
        return ResultUtils.success(userService.login(userLoginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        return ResultUtils.success(userService.register(userRegisterRequest));
    }

}
