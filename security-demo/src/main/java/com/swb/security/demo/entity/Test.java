package com.swb.security.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author swb
 */
@Data
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    @Column(name = "create_Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

}