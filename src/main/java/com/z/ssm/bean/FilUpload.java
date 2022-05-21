package com.z.ssm.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Cai Peishen
 * @Date: 2022/5/21 11:18
 * @Description:
 **/
@Data
public class FilUpload {

    private Employee employee;

    private MultipartFile headerImg;

}
