/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SAGAR
 */
public class Sevletdemo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int a=4;
        PrintWriter out=response.getWriter();       
       
        
        String name=request.getParameter("uname");
        
         //First Way
        
//        request.setAttribute("num1", a);  
        
        //Second way for global use
        
        ServletContext sc = getServletContext();
        sc.setAttribute("num1", a);
        
        
        RequestDispatcher rd=request.getRequestDispatcher("Serv");
        rd.forward(request, response);
    }
}
