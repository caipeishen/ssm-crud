<%--
  Created by IntelliJ IDEA.
  User: ZhouJie
  Date: 2021/3/7
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <% pageContext.setAttribute("APP_PATH",request.getContextPath()); %>
    <%--
        不以 / 开始的相对路径找资源，以当前资源的路径为基准，经常容易出问题
        推荐使用以 / 开始，即以服务器的路径为标准(http://localhost:3306/)
    --%>
    <%--引入jQuery--%>
    <script src="${APP_PATH}/static/js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <%--引入样式Bootstarp--%>
    <link rel="stylesheet" href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
    <script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/manage.css"/>
</head>
<body>
<div>
    <div class="tou">
        <div class="user">
            <span class="text">企业员工管理系统</span>
            <img class="user_logo" src="${APP_PATH}/static/img/logo.png" alt="">
            <span class="user_name">${sessionScope.admin.adminName}管理员</span>
            <a href="${APP_PATH}/adminExit"><span class="glyphicon glyphicon-off exit" aria-hidden="true"></span></a>
        </div>
    </div>

    <!-- 员工添加模态框 -->
    <div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">员工添加</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">员工姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="empName" id="empName_add_input" placeholder="员工姓名">
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">入职日期</label>
                            <div class="col-sm-10">
                                <input type="date" class="form-control" name="hireDate" id="hire_date_add_input" placeholder="入职日期">
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">工资</label>
                            <div class="col-sm-10">
                                <input type="number" class="form-control" name="salary" id="salary_add_input" placeholder="工资">
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10">
                                <label class="radio-inline">
                                    <input type="radio" name="gender" checked="checked" id="gender1_add_input" value="男"> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="gender" id="gender2_add_input" value="女"> 女
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">所属部门</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="deptId" id="depts_add_select">
                                </select>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 员工修改模态框 -->
    <div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">员工修改</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">头像</label>
                            <div class="col-sm-10">
                                <img src="${pageContext.request.contextPath }/static/img/logo.png" alt="选择并上传头像" id="photo_update_input" style="width: 100px;height: 100px;left:0;top: 0;"/>
                                <input type="file" id="avatar_file" name="avatar_file"
                                       accept="image/jpg,image/png,image/gif"
                                       style="width: 100%;height: 100px;opacity: 0;position: absolute;left:0px;top: 0;"/>
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">员工姓名</label>
                            <div class="col-sm-10">
                                <input class="form-control" type="text" id="empName_update_input" disabled>
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">入职日期</label>
                            <div class="col-sm-10">
                                <input type="date" class="form-control" name="hireDate" id="hire_date_update_input">
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">工资</label>
                            <div class="col-sm-10">
                                <input type="number" class="form-control" name="salary" id="salary_update_input">
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10">
                                <label class="radio-inline">
                                    <input type="radio" name="gender" id="gender1_update_input" value="男"> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="gender" id="gender2_updateinput" value="女"> 女
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">部门</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="deptId" id="depts_update_select">
                                </select>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
                </div>
            </div>
        </div>
    </div>


    <div class="container" style="background-color: #FFF">
        <!-- 标题行 -->
        <div class="row">
            <div class="col-md-10">
                <h1>员工数据</h1>
            </div>
        </div>
        <!-- 操作行 -->
        <div class="row">
            <div class="col-md-4 col-md-offset-8">
                <button class="btn btn-success" type="button" id="emp_add_modal_btn">新增</button>
                <button class="btn btn-danger" type="button" id="emp_delete_all_btn">删除</button>
            </div>

        </div>
        <!-- 表格行 -->
        <div class="row">
            <div class="col-md-12">
                <table  class="table table-hover table-striped" id="emps_table">
                    <thead>
                        <tr class="info">
                            <th><input type="checkbox" class="checkbox" id="check_all"></th>
                            <th>ID</th>
                            <th>员工姓名</th>
                            <th>员工性别</th>
                            <th>入职日期</th>
                            <th>工资</th>
                            <th>部门名称</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
        <!-- 分页行 -->
        <div class="row">
            <%--分页信息--%>
            <div class="col-md-4"><p id="page_info_area"></p></div>
            <%--分页导航--%>
            <div class="col-md-5 col-md-offset-3" id="page_nav_area"></div>
        </div>

    </div>
</div>
</body>
<script src="${APP_PATH}/static/js/manage.js" type="text/javascript" charset="utf-8"></script>
<script>const APP_PATH = "${APP_PATH}";</script>
</html>
