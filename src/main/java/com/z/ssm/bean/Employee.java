package com.z.ssm.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private Integer empId;

    @Pattern(regexp = "(^[a-zA-Z0-9_]{6,8}$)|(^[\\u2E80-\\u9FFF]{2,5}$)",
            message = "用户名必须是唯一的或是6-8位数字 英文 _组合,或2-5位汉字")
    private String empName;

    private String gender;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date hireDate;

    private BigDecimal salary;

    private Integer deptId;

    private Integer status;

    private String photo;

    private Department department;

    private Date createDate;

    private Date updateDate;

    public Employee(Integer empId, String empName, String gender, Date hireDate, BigDecimal salary,  Integer deptId) {
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.hireDate = hireDate;
        this.salary = salary;
        this.deptId = deptId;
    }
}