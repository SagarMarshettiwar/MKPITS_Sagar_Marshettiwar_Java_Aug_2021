/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.form1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SAGAR
 */
public class Form1 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String name=request.getParameter("name");
            String pass=request.getParameter("pname");
            String gender=request.getParameter("gender");
//            String course1=request.getParameter("cb1");
//            String course2=request.getParameter("cb2");
//            String course3=request.getParameter("cb3");
            String city=request.getParameter("city");
            String disc=request.getParameter("disc");
            String course[]=request.getParameterValues("cb");
//            for(String s:course){
//                out.println(s);
//            }
            
            out.println("<table >");
            out.println("<tr>");
            out.println("<td>name:</td>");
            out.println("<td>"+name+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Password:</td>");
            out.println("<td>"+pass+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Gender:</td>");
            out.println("<td>"+gender+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Course:</td>");
             for(String s:course){  
             out.println("<td>"+s+"</td>");
            }
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>City:</td>");
            out.println("<td>"+city+"</td>");
            out.println("</tr>");            
            out.println("<tr>");
            out.println("<td>Discription:</td>");
            out.println("<td>"+disc+"</td>");
            out.println("</tr>");
            out.println("</table>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
