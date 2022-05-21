package com.z.ssm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yang Huihui
 * @create 2022-05-10-19:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private String adminName;
    private String adminUsername;
    private String adminPassword;
}
