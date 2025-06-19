package top.liuqiao.nextTop.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liuqiao.nextTop.common.BaseResponse;
import top.liuqiao.nextTop.common.ResultUtils;
import top.liuqiao.nextTop.model.request.CheckInRequest;
import top.liuqiao.nextTop.service.CheckInRecordService;

/**
 * @author liuqiao
 * @since 2025-06-19
 */
@RequestMapping("/check")
@RestController
@AllArgsConstructor
public class CheckInRecordController {

    private final CheckInRecordService checkInRecordService;


    @PostMapping("/in")
    public BaseResponse<Void> checkIn(@RequestBody @Validated CheckInRequest checkInRequest) {
        checkInRecordService.checkIn(checkInRequest);
        return ResultUtils.success(null);
    }
}
