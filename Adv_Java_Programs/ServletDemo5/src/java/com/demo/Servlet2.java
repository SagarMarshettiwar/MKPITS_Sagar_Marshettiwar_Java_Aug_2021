/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SAGAR
 */
public class Servlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out=response.getWriter();
            out.println("Servlet 2");
            out.println(request.getParameter("uname"));
            
            // First way
            
//          out.println(request.getAttribute("num1"));

            //Application Level or Globa
            ServletContext sc = getServletContext();
             out.println(sc.getAttribute("num1"));
    }
}