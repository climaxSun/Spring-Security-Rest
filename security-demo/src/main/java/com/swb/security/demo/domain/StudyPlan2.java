package com.swb.security.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>文件  StudyPlan2</p>
 * <p>时间  2020-05-28 15:58:56</p>
 *
 * @author 123
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyPlan2 {
    private int id;
    private String technology;
    private String data;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String result;
    private String remark;
}
