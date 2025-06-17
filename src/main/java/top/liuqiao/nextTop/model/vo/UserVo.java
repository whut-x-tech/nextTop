package top.liuqiao.nextTop.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuqiao
 * @since 2025-06-17
 */
@Data
public class UserVo implements Serializable {

    /**
     *
     */
    private String username;


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


    private final static long serialVersionUID = -1;
}
