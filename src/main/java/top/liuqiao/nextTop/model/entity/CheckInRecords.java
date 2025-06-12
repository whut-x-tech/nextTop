package top.liuqiao.nextTop.model.entity;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_check_in_records
 */
@Data
public class CheckInRecords {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Date checkInTime;

    /**
     * 
     */
    private Integer studyTime;

    /**
     * 
     */
    private String studyRecord;

    /**
     * 
     */
    private Integer resumeSubmissions;

    /**
     * 
     */
    private Integer interviewRecord;

    /**
     * 
     */
    private String interviewDetails;

    /**
     * 
     */
    private Integer offer;

    /**
     * 
     */
    private String offerDetails;
}