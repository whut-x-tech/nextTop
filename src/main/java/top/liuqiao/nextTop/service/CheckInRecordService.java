package top.liuqiao.nextTop.service;

import top.liuqiao.nextTop.model.entity.CheckInRecords;
import top.liuqiao.nextTop.model.request.CheckInRequest;

import java.util.List;

/**
 * @author liuqiao
 * @since 2025-06-19
 */
public interface CheckInRecordService {
    void checkIn(CheckInRequest checkInRequest);

    List<CheckInRecords> getRecommendations();

    boolean putRecommendations(Long id);
}
