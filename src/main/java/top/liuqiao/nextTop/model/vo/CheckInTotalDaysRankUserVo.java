package top.liuqiao.nextTop.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckInTotalDaysRankUserVo implements Serializable {
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
    private Integer totalDays;


    private final static long serialVersionUID = -1;
}