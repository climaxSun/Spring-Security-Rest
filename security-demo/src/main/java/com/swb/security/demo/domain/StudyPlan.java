package com.swb.security.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author swb
 * 时间  2020-04-09 18:01
 * 文件  StudyPlan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlan {

    private int id;
    private String technology;
    private String data;
    private Date startDate;
    private Date endDate;
    private String result;
    private String remark;

}
