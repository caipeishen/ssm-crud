package com.z.ssm.dao;

import com.z.ssm.bean.Department;
import com.z.ssm.bean.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {

    List<Department> selectByExample(DepartmentExample example);

}