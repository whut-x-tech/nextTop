package top.liuqiao.nextTop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.liuqiao.nextTop.common.ErrorCode;
import top.liuqiao.nextTop.exception.BusinessException;
import top.liuqiao.nextTop.exception.ThrowUtils;
import top.liuqiao.nextTop.mapper.UserMapper;
import top.liuqiao.nextTop.model.entity.User;
import top.liuqiao.nextTop.model.request.UserLoginRequest;
import top.liuqiao.nextTop.model.request.UserRegisterRequest;
import top.liuqiao.nextTop.model.vo.CheckInConsecutiveDaysRankUserVo;
import top.liuqiao.nextTop.model.vo.CheckInTotalDaysRankUserVo;
import top.liuqiao.nextTop.model.vo.UserVo;
import top.liuqiao.nextTop.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final StringRedisTemplate redisTemplate;

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
        user.setLastCheckInTime(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 86400000)));

        // 唯一索引确保用户注册情况
        int row = userMapper.addUser(user);
        ThrowUtils.throwIf(row == 0,
                ErrorCode.PARAMS_ERROR, "用户已经注册"); // 配合 unique index 实现去重

        return Boolean.TRUE;
    }

    @Override
    public UserVo getVo(Long id) {
        User user = userMapper.getUserVoById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在");

        HashMap<String, String> map = new HashMap<>();
        Cursor<String> scan = null;
        try {
            scan = redisTemplate
                    .scan(ScanOptions.scanOptions()
                            .match("checkIn:" + user.getId() + ":*")
                            .count(100)
                            .build());
            List<String> idList = new ArrayList<>();
            while (scan.hasNext()) {
                scan.forEachRemaining(idList::add);
                List<String> checkList = redisTemplate.opsForValue().multiGet(idList);
                idList = idList.stream().map(s -> s.substring(28)).collect(Collectors.toList());
                for (int i = 0; i < checkList.size(); i++) {
                    map.put(idList.get(i), checkList.get(i));
                }
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "redis 错误");
        } finally {
            assert scan != null;
            scan.close();
        }


        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class, "checkInHistory");
        userVo.setCheckInHistory(map);
        return userVo;
    }

    @Override
    public List<CheckInTotalDaysRankUserVo> getTotalDayRank() {
        List<User> userList = userMapper.getTotalDayRank();
        return userList
                .stream()
                .map(s -> BeanUtil.copyProperties(s, CheckInTotalDaysRankUserVo.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CheckInConsecutiveDaysRankUserVo> getConsecutiveDaysRank() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime
                .of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                        0, 0, 0, 0).minusDays(1);

        List<User> userList = userMapper.getConsecutiveDaysRank(yesterday.toString());
        return userList
                .stream()
                .map(s -> BeanUtil.copyProperties(s, CheckInConsecutiveDaysRankUserVo.class))
                .collect(Collectors.toList());
    }
}
