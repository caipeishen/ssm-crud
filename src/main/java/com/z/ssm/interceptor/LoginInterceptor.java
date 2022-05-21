package com.z.ssm.interceptor;

import com.z.ssm.bean.Admin;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yang Huihui
 *
 * @create 2022-05-11-16:54
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null){
            response.sendRedirect("/index.jsp");
            return false;
        }
        return true;
    }
}
