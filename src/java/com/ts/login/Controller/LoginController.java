/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.login.Controller;

import com.ts.login.Model.LoginModel;
import com.ts.webservice.TrafficSignalWebServices;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shruti
 */
public class LoginController extends HttpServlet {
 public static String start_stop1="start";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginModel loginModel = new LoginModel();
        ServletContext ctx = getServletContext();
        loginModel.setDriverClass(ctx.getInitParameter("driverClass"));
        loginModel.setConnectionString(ctx.getInitParameter("connectionString"));
        loginModel.setDb_userName(ctx.getInitParameter("db_userName"));
        loginModel.setDb_userPassword(ctx.getInitParameter("db_userPassword"));
        loginModel.setConnection();
        String task = request.getParameter("task");
   HttpSession session = request.getSession();
        if (task == null) {
            task = "";
        }
         String designation = null;       
        if (task.equals("Login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
               
           //  session.setAttribute("user_name", username);
          //   session.setAttribute("password", password);
            boolean userAuthentic = loginModel.isUserAuthentic(username, password);
               designation=loginModel.getDesignation(username,password);
            if (userAuthentic == true) {
                if(designation.equals("admin")){
              //  designation="admin";
                    session.setAttribute("login_designation", designation);
                }else{
               // designation="user";
                    session.setAttribute("login_designation", designation);
                   // session.setMaxInactiveInterval(60);
                }
                request.getRequestDispatcher("/after_login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", loginModel.getMessage());
                request.setAttribute("msgBgColor", loginModel.getMsgBgColor());
                request.getRequestDispatcher("/beforeLoginView").forward(request, response);
            }
        }
         
         if(task.equals("stopWebService"))
        {
            start_stop1 = "stop";
        System.out.println("StopWebServiceResponse task");
          request.setAttribute("show_button", "start");
        request.getRequestDispatcher("/after_login.jsp").forward(request, response);
         
        }
          if(task.equals("startWebService"))
        {
        TrafficSignalWebServices ts = new  TrafficSignalWebServices();
        start_stop1 = "start";
        System.out.println("StartWebServiceResponse task");
          request.setAttribute("show_button", "stop");    
          request.getRequestDispatcher("/after_login.jsp").forward(request, response);
   
        }
          
  
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
