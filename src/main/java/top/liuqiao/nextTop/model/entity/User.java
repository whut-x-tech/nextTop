package top.liuqiao.nextTop.model.entity;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_user
 */
@Data
public class User {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String account;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private Integer totalDays;

    /**
     * 
     */
    private Integer consecutiveDays;

    /**
     * 
     */
    private Date lastCheckInTime;

    /**
     * 
     */
    private String checkInHistory;
}