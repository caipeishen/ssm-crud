package com.z.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.z.ssm.bean.Employee;
import com.z.ssm.bean.Message;
import com.z.ssm.service.EmployeeService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.*;

/**
 * @author Yang Huihui
 *
 * @create 2022-05-10-19:30
 */
@ResponseBody
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 删除员工，也可以做批量删除
     * 单个删除 1
     * 批量删除 1-2-3进行解析
     * @param ids
     * @return
     */
    @DeleteMapping("/emp/{ids}")
    public Message deleteEmpById(@PathVariable("ids") String ids){
        if (ids != null){
            //批量删除
            if (ids.contains("-")){
                //创建id集合
                List<Integer> del_ids = new ArrayList<>();
                String[] str_id = ids.split("-");
                for (String str : str_id){
                    del_ids.add(Integer.parseInt(str));
                }
                employeeService.deleteBath(del_ids);
            }else{
                //单个删除
                employeeService.deleteEmp(Integer.parseInt(ids));
            }
            return Message.success();
        }
        return Message.fail();
    }


    /**
     * 员工更新
     * @param employee
     * @return
     */
    @PutMapping("emp/{empId}")
    public Message saveEmp(Employee employee){
        employeeService.update(employee);
        return Message.success();
    }

//
//    /**
//     * 员工更新
//     * @param employee
//     * @return
//     */
//    @PutMapping("emp/{empId}")
//    public Message saveEmp(@RequestParam(value="employee",required=false) Employee employee, @RequestParam(value="headerImg",required=false) MultipartFile headerImg, HttpSession session){
//        String path = session.getServletContext().getRealPath("static"+ File.separator+"img");
//
//        String oldFileName = headerImg.getOriginalFilename();//获取原文件名
//        String prefix = FilenameUtils.getExtension(oldFileName);//获取后缀名
//        if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("pneg") ){
//            String fileName = System.currentTimeMillis()+ UUID.randomUUID().toString() +"_Employee.jpg";//新文件名
//            File file = new File(path,fileName);
//            if(!file.exists()){
//                file.mkdirs();//创建目录
//            }
//            try {
//                headerImg.transferTo(file);//真正的上传文件核心代码
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("fileName:"+ fileName);
//            employee.setPhoto(fileName);
//            employeeService.update(employee);
//        } else {
//            //上传的格式不对
//            return Message.fail();
//        }
//        return Message.success();
//    }


    /**
     *根据Id查询员工
     * @param empId
     * @return
     */
    @GetMapping("/emp/{empId}")
    public Message getEmp(@PathVariable("empId") Integer empId){
        return Message.success().add("emp",employeeService.getEmp(empId));
    }

    /**
     * 判断当前用户名是否可用
     * @param empName
     * @return
     */
    @PostMapping("/checkUser")
    public Message checkUser(@RequestParam("empName") String empName){
        //由于前端也设置了信息校验，所以后端校验应该再前端校验的通过后进行
        String regx = "(^[a-zA-Z0-9_]{6,8}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if (!empName.matches(regx)){
            return Message.fail().add("va_msg","用户名只能是唯一的或是6-8位数字 英文 _组合,或2-5位汉字");
        }
        //真正的后端数据库校验
        return employeeService.checkEmpName(empName) ?
                Message.success() : Message.fail().add("va_msg","当前用户名不可用");
    }

    /**
     *  保存信息
     * @param employee
     * @return
     */
    @PostMapping("/emp")
    public Message saveEmp(@Valid Employee employee, BindingResult result){
        System.out.println(employee);
        if (result.hasErrors()){
            //JSR303校验失败，在模态框中显示校验失败的信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors){
                System.out.println("错误的字段名： "+fieldError.getField());
                System.out.println("错误信息： "+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Message.fail().add("errorFields",map);
        }
        employeeService.saveEmp(employee);
        return Message.success();
    }

    /**
     * 员工信息查询
     * @param pn
     * @return
     */
    @GetMapping("/emps")
    public Message getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        //使用分页插件，在查询之前 只需要调用startPage()传入页码以及每页的大小
        PageHelper.startPage(pn,6);
        List<Employee> employees = employeeService.getAll();
        //使用PageInfo保证查询出的结果
        PageInfo page = new PageInfo(employees, 5);
        return Message.success().add("pageInfo",page);
    }


    /**
     * 换头像
     */
    @ResponseBody
    @RequestMapping("/changeHeaderImg")
    public String changeHeaderImg(@RequestParam(value="file",required=false) MultipartFile headerImg, HttpSession session, Map<String,String> map) throws Exception {
        String path = session.getServletContext().getRealPath("statics"+ File.separator+"img");

        String oldFileName = headerImg.getOriginalFilename();//获取原文件名
        String prefix = FilenameUtils.getExtension(oldFileName);//获取后缀名
        if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("pneg") ){
            String fileName = System.currentTimeMillis()+ UUID.randomUUID().toString() +"_Employee.jpg";//新文件名
            File file = new File(path,fileName);
            if(!file.exists()){
                file.mkdirs();//创建目录
            }
            try {
                headerImg.transferTo(file);//真正的上传文件核心代码
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileName;

        }else{
            //上传的格式不对
            throw new Exception("上传的图片格式不正确");
        }
    }
}
