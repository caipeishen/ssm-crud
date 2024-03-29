package com.z.ssm.service;

import com.z.ssm.bean.Admin;
import com.z.ssm.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yang Huihui
 *
 * @create 2022-05-10-19:32
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登入
     * @param admin
     * @return
     */
    public Admin login(Admin admin) {
        return adminMapper.login(admin);
    }
}
