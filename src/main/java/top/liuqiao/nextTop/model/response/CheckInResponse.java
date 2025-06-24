package top.liuqiao.nextTop.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckInResponse {
    private String id;
    private String userAccount;
    private Date checkInTime;
    private Integer studyTime;
    private String studyRecord;
    private Integer resumeSubmissions;
    private Integer interviewRecord;
    private String interviewDetails;
    private Integer offer;
    private String offerDetails;
}
