package top.liuqiao.nextTop.service;

import top.liuqiao.nextTop.model.vo.CheckInConsecutiveDaysRankUserVo;
import top.liuqiao.nextTop.model.vo.CheckInTotalDaysRankUserVo;
import top.liuqiao.nextTop.model.request.UserLoginRequest;
import top.liuqiao.nextTop.model.request.UserRegisterRequest;
import top.liuqiao.nextTop.model.vo.UserVo;

import java.util.List;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
public interface UserService {
    Long login(UserLoginRequest userLoginRequest);

    Boolean register(UserRegisterRequest userRegisterRequest);

    UserVo getVo(Long id);

    List<CheckInTotalDaysRankUserVo> getTotalDayRank();

    List<CheckInConsecutiveDaysRankUserVo> getConsecutiveDaysRank();
}
