package com.z.ssm.service;

import com.z.ssm.bean.Employee;
import com.z.ssm.bean.EmployeeExample;
import com.z.ssm.dao.EmployeeMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yang Huihui
 *
 * @create 2022-05-10-19:32
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有员工信息
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 保存员工
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 校验用户名是否可用
     * @param empName
     * @return
     */
    public boolean checkEmpName(String empName) {
        //创建EmployeeExample对象并创建其内部类的对象
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //调用判断EmpName的方法传入参数empName
        criteria.andEmpNameEqualTo(empName);
        //通过条件去查找记录数
        long count = employeeMapper.countByExample(example);
        //没有记录数则返回true
        return count == 0 ? true : false;
    }


    /**
     * 通过员工Id查询员工信息
     * @param empId
     * @return
     */
    public Employee getEmp(Integer empId) {
        return employeeMapper.selectByPrimaryKey(empId);
    }

    /**
     * 员工更新
     * @param employee
     */
    public void update(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBath(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in(1,2,3,..);
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }

}
