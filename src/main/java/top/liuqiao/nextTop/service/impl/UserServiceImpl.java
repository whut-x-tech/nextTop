package top.liuqiao.nextTop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.liuqiao.nextTop.common.ErrorCode;
import top.liuqiao.nextTop.exception.ThrowUtils;
import top.liuqiao.nextTop.mapper.UserMapper;
import top.liuqiao.nextTop.model.entity.User;
import top.liuqiao.nextTop.model.request.UserLoginRequest;
import top.liuqiao.nextTop.model.request.UserRegisterRequest;
import top.liuqiao.nextTop.model.vo.UserVo;
import top.liuqiao.nextTop.service.UserService;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Long login(UserLoginRequest userLoginRequest) {
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();


        User user = userMapper.selectByAccount(account);

        ThrowUtils.throwIf(user == null,
                ErrorCode.PARAMS_ERROR, "用户不存在");

        ThrowUtils.throwIf(!user.getPassword().equals(SecureUtil.md5(password)),
                ErrorCode.PARAMS_ERROR, "账号或者密码错误");

        return user.getId();
    }

    @Override
    public Boolean register(UserRegisterRequest userRegisterRequest) {
        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String username = userRegisterRequest.getUsername();
        String avatarUrl = userRegisterRequest.getAvatarUrl();

        User user = new User();
        user.setId(IdUtil.getSnowflakeNextId());
        user.setAccount(account);
        user.setPassword(SecureUtil.md5(password));
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);

        // 唯一索引确保用户注册情况
        int row = userMapper.addUser(user);
        ThrowUtils.throwIf(row == 0, ErrorCode.PARAMS_ERROR, "用户已经注册");

        return Boolean.TRUE;
    }

    @Override
    public UserVo getVo(Long id) {
        User user = userMapper.getUserVoById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在");
        return BeanUtil.copyProperties(user, UserVo.class);
    }
}
