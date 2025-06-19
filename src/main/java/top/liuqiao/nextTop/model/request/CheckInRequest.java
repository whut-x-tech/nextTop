package top.liuqiao.nextTop.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
public class CheckInRequest implements Serializable {

    /**
     *
     */
    @Min(1)
    private Long userId;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkInTime;

    /**
     *
     */
    @Min(0)
    private Integer studyTime;

    /**
     *
     */
    @Length(max = 2 << 20)
    private String studyRecord;

    /**
     *
     */
    @Min(0)
    private Integer resumeSubmissions;

    /**
     *
     */
    @Min(0)
    @Max(1)
    private Integer interviewRecord;

    /**
     *
     */
    @Length(max = 2 << 20)
    private String interviewDetails;

    /**
     *
     */
    @Min(0)
    @Max(1)
    private Integer offer;

    /**
     *
     */
    @Length(max = 2 << 20)
    private String offerDetails;


    private final static long serialVersionUID = -1;
}