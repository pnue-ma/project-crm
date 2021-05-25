package com.my.crm.web.filter;

import com.my.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入验证是否登录过滤器");
        //通过获取session域中的user对象，判断user对象是否为空来判断用户的登录状态以及会话状态是否保持
        HttpServletRequest request= (HttpServletRequest) servletRequest;            //父类对象无法获得session，需要强转成子类
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        User user= (User) request.getSession().getAttribute("user");
        if (null == user){
            String root=request.getContextPath();
//            System.out.println(root+"/login.jsp================="+request.getRequestURI()+"------"+(root+"/login.jsp").equals(request.getRequestURI()));
            if (!(root+"/login.jsp").equals(request.getRequestURI()) && !(root+"/settings/user/login.do").equals(request.getRequestURI())){
                response.sendRedirect(root+"/login.jsp");          //请求转发与重定向的路径？

            }else {
                filterChain.doFilter(request,response);
            }
        }else {
            filterChain.doFilter(request,response);
        }

    }

    public void destroy() {

    }
}
