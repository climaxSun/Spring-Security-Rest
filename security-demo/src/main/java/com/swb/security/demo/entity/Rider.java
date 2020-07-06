package com.swb.security.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author swb
 * @date 2020-04-21 16:05
 */
@Data
@Table(name = "rider")
public class Rider {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "test_id")
    private Integer testId;

    private String name;
}