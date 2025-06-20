package top.liuqiao.nextTop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import top.liuqiao.nextTop.common.ErrorCode;
import top.liuqiao.nextTop.exception.ThrowUtils;
import top.liuqiao.nextTop.mapper.CheckInRecordsMapper;
import top.liuqiao.nextTop.mapper.UserMapper;
import top.liuqiao.nextTop.model.entity.CheckInRecords;
import top.liuqiao.nextTop.model.entity.User;
import top.liuqiao.nextTop.model.request.CheckInRequest;
import top.liuqiao.nextTop.service.CheckInRecordService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author liuqiao
 * @since 2025-06-19
 */
@Service
@AllArgsConstructor
public class CheckInRecordServiceImpl implements CheckInRecordService {

    private final CheckInRecordsMapper checkInRecordsMapper;

    private final UserMapper userMapper;

    private final TransactionTemplate transactionTemplate;

    private final RedissonClient redissonClient;

    private final StringRedisTemplate redisTemplate;

    @Override
    public void checkIn(CheckInRequest checkInRequest) {

        Long userId = checkInRequest.getUserId();
        // todo lock
        RLock lock = redissonClient.getLock("checkin:" + userId);
        ThrowUtils.throwIf(!lock.tryLock(),
                ErrorCode.OPERATION_ERROR, "签到时还有没有处理的其他签到操作");


        try {
            // get user
            User user = userMapper.getUserVoById(userId);
            ThrowUtils.throwIf(user == null,
                    ErrorCode.PARAMS_ERROR, "用户不存在");

            // check num update
            LocalDate lastCheckInTime = user.getLastCheckInTime()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // same
            CheckInRecords checkInRecords = BeanUtil.copyProperties(checkInRequest, CheckInRecords.class);
            checkInRecords.setId(IdUtil.getSnowflakeNextId());

            user.setLastCheckInTime(Date.from(Instant.now()));
            user.setCheckInHistory(""); // todo 历史记录暂时不处理

            // difference
            LocalDate nowDate = LocalDate.now();
            boolean firstTimeToday = lastCheckInTime.isBefore(nowDate);
            if (firstTimeToday) {
                // check at different day
                if (lastCheckInTime.plusDays(1).isEqual(nowDate)) {
                    // succession check in
                    user.setConsecutiveDays(user.getConsecutiveDays() + 1);
                } else {
                    user.setConsecutiveDays(1);
                }
                user.setTotalDays(user.getTotalDays() + 1);
            }


            String key = userId + ":" + nowDate.getYear() + ":" + nowDate.getMonthValue();
            transactionTemplate.execute(s -> {
                Object head = s.createSavepoint();
                try {
                    userMapper.updateUserChecKHistoryConsecutiveDaysTotalDaysById(user);
                    checkInRecordsMapper.insert(checkInRecords);

                    if (firstTimeToday) {
                        // 存储用户签到信息
                        redisTemplate.opsForValue()
                                .setBit(key, nowDate.getDayOfMonth(), true);
                    }

                } catch (Exception e) {
                    s.rollbackToSavepoint(head);


                    if (firstTimeToday) {
                        // 回滚操作
                        redisTemplate.opsForValue()
                                .setBit(key, nowDate.getDayOfMonth(), false);
                    }

                    throw new RuntimeException(e);
                }
                return null;
            });
        } finally {
            lock.unlock();
        }
    }
}
