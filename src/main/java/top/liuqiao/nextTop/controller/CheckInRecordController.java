package top.liuqiao.nextTop.controller;

import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liuqiao.nextTop.common.BaseResponse;
import top.liuqiao.nextTop.common.ResultUtils;
import top.liuqiao.nextTop.model.entity.CheckInRecords;
import top.liuqiao.nextTop.model.request.CheckInRequest;
import top.liuqiao.nextTop.model.vo.CheckInConsecutiveDaysRankUserVo;
import top.liuqiao.nextTop.model.vo.CheckInTotalDaysRankUserVo;
import top.liuqiao.nextTop.service.CheckInRecordService;
import top.liuqiao.nextTop.service.UserService;

import java.util.List;

/**
 * @author liuqiao
 * @since 2025-06-19
 */
@RequestMapping("/check")
@RestController
@AllArgsConstructor
public class CheckInRecordController {

    private final CheckInRecordService checkInRecordService;

    private final UserService userService;


    @PostMapping("/in")
    public BaseResponse<Void> checkIn(@RequestBody @Validated CheckInRequest checkInRequest) {
        checkInRecordService.checkIn(checkInRequest);
        return ResultUtils.success(null);
    }

    @PostMapping("/totalDaysRank")
    public BaseResponse<List<CheckInTotalDaysRankUserVo>> getTotalDayRank() {
        return ResultUtils.success(userService.getTotalDayRank());
    }

    @PostMapping("/consecutiveDaysRank")
    public BaseResponse<List<CheckInConsecutiveDaysRankUserVo>> getConsecutiveDaysRank() {
        return ResultUtils.success(userService.getConsecutiveDaysRank());
    }

    @GetMapping("/getRecommendations")
    public BaseResponse<List<CheckInRecords>> getRecommendations() {
        List<CheckInRecords> checkInRecords = checkInRecordService.getRecommendations();
        if(CollectionUtils.isEmpty(checkInRecords)){
            return ResultUtils.error(400,"暂无推荐");
        }
        return ResultUtils.success(checkInRecords);
    }

    @PostMapping("/putRecommendations")
    public BaseResponse<Void> putRecommendations(@RequestParam("id") Long id) {
        boolean isSuccess = checkInRecordService.putRecommendations(id);
        if(isSuccess){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(400,"推荐失败");
    }

    /**
     * 获取所有签到记录
     * @return
     */
    @GetMapping("/getAllRec")
    public BaseResponse getAll() {
        List<CheckInRecords> checkInRecords = checkInRecordService.getAllRecords();
        if (CollectionUtils.isEmpty(checkInRecords)) {
            return ResultUtils.error(400, "暂无记录");
        }
        return ResultUtils.success(checkInRecords);
    }
}
