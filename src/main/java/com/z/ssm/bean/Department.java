package com.z.ssm.bean;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Department {

    private Integer deptId;

    private String deptName;

    private Integer status;

    private Date createDate;

    private Date updateDate;

}