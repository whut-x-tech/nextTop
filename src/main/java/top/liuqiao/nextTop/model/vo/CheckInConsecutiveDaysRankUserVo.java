package top.liuqiao.nextTop.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckInConsecutiveDaysRankUserVo implements Serializable {
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
    private Integer consecutiveDays;


    private final static long serialVersionUID = -1;
}