package com.my.crm.settings.web.controller;

import com.my.crm.exceptions.UserLoginException;
import com.my.crm.settings.domain.User;
import com.my.crm.settings.service.UserService;
import com.my.crm.settings.service.impl.UserServiceImpl;
import com.my.crm.utils.TransactionInvocationHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//模板模式
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入UserController的service()方法");
        String servletPath=request.getServletPath();
        if("/settings/user/login.do".equals(servletPath)){
            //如果用户请求的是controller：login.do的路径，则调用具体方法处理用户登录请求
            userLogin(request,response);            //这为啥不用创建对象，使用对象调用方法？（不是我自己创建的对象调用我写的方法，而是tomcat创建的对象调用该方法？tomcat最终会给所有方法添加一个主语？）
        }else  if("/settings/user/xx.do".equals(servletPath)){
            //
        }
    }

    private void userLogin(HttpServletRequest request,HttpServletResponse response) {
        //从request域中获取从前端传来的参数
        String uname=request.getParameter("uname");
        String upass=request.getParameter("upass");     //这两个request域中的对象是我作为开发人员手动存进去的，以下这个是tomcat自动为我维护由tomcat为我存进去的
        String uip=request.getRemoteAddr();

        //核心业务逻辑，调用service层完成
        UserService userService= (UserService) new TransactionInvocationHandler(new UserServiceImpl()).getProxy();      //使用动态代理对象，此时的userService对象在内存中非UserServiceImpl形态，而是UserServiceImpl的代理类形态了
        String jsonStr=null;
        PrintWriter out= null;
        try {
            User user=userService.userLogin(uname,upass,uip);

            //程序在这里还能往下走，说明用户登录没有异常，即允许用户成功登录
            request.getSession().setAttribute("user",user);     //{"ifSuccess":"1"}
            jsonStr="{\"ifSuccess\":\"1\"}";
            out=response.getWriter();
            out.print(jsonStr);
        } catch (Exception e) {
            //向前端返回失败的登录状态与异常信息,以json格式{"ifSuccess":"0","failMsg":""}
            jsonStr="{\"ifSuccess\":\"0\",\"failMsg\":\""+e.getMessage()+"\"}";
            try {
                out = response.getWriter();
                out.print(jsonStr);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                out.flush();
                out.close();
            }
            e.printStackTrace();
        }
    }
}
