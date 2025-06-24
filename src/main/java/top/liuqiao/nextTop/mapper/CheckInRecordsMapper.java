package top.liuqiao.nextTop.mapper;

import org.apache.ibatis.annotations.Param;
import top.liuqiao.nextTop.model.entity.CheckInRecords;

import java.util.List;

/**
* @author laowang
* @description 针对表【t_check_in_records】的数据库操作Mapper
* @createDate 2025-06-12 21:08:20
* @Entity top.liuqiao.nextTop.model.entity.CheckInRecords
*/
public interface CheckInRecordsMapper {

    void insert(CheckInRecords checkInRecords);

    List<CheckInRecords> selectByUserId(Long userId);

    List<CheckInRecords> selectById(Long id);
    List<CheckInRecords> selectByIds(@Param("ids") List<Long> ids);


}




