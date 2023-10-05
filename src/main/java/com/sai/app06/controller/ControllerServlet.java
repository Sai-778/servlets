package com.sai.app06.controller;


import com.sai.app06.beans.Employee;
import com.sai.app06.factory.ConnectionFactory;
import com.sai.app06.factory.EmployeeDaoFactory;
import com.sai.app06.factory.EmployeeServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sai.app06.service.EmployeeService;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(value = "*.do", loadOnStartup = 1)
public class ControllerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ConnectionFactory.getConnection();
        EmployeeDaoFactory.getEmployeeDao();
        EmployeeServiceFactory.getEmployeeService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req,resp);
    }




    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        int eno= 0;
        String ename = "";
        float esal = 0.0f;
        String eaddr = "";
        Employee employee = null;
        String status = "";
        RequestDispatcher requestDispatcher = null;


        EmployeeService employeeService = EmployeeServiceFactory.getEmployeeService();
        String requestURI = request.getRequestURI();

        if(requestURI.endsWith("add.do")){


            eno = Integer.parseInt(request.getParameter("eno"));
            ename = request.getParameter("ename");
            esal = Float.parseFloat(request.getParameter("esal"));
            eaddr = request.getParameter("eaddr");


            employee = new Employee();
            employee.setEno(eno);
            employee.setEname(ename);
            employee.setEsal(esal);
            employee.setEaddr(eaddr);


            status = employeeService.addEmployee(employee);
            if(status.equalsIgnoreCase("success")){
                requestDispatcher = request.getRequestDispatcher("success.html");
                requestDispatcher.forward(request, response);
            }
            if(status.equalsIgnoreCase("failure")){
                requestDispatcher = request.getRequestDispatcher("failure.html");
                requestDispatcher.forward(request, response);
            }
            if(status.equalsIgnoreCase("existed")){
                requestDispatcher = request.getRequestDispatcher("existed.html");
                requestDispatcher.forward(request, response);
            }


        }
        if(requestURI.endsWith("search.do")){
            eno = Integer.parseInt(request.getParameter("eno"));
            employee = employeeService.searchEmployee(eno);
            if(employee == null){
                requestDispatcher = request.getRequestDispatcher("notexisted.html");
                requestDispatcher.forward(request, response);
            }else{
                out.println("<html>");
                out.println("<body>");
                out.println("<br><br><br>");
                out.println("<table style='margin-left: auto; margin-right: auto;' border='1'");
                out.println("<tr><td>Employee Number</td><td>"+employee.getEno()+"</td></tr>");
                out.println("<tr><td>Employee Name</td><td>"+employee.getEname()+"</td></tr>");
                out.println("<tr><td>Employee Salary</td><td>"+employee.getEsal()+"</td></tr>");
                out.println("<tr><td>Employee Address</td><td>"+employee.getEaddr()+"</td></tr>");
                out.println("</table></body></html>");
            }
        }
        if(requestURI.endsWith("editform.do")){
            eno = Integer.parseInt(request.getParameter("eno"));
            employee = employeeService.searchEmployee(eno);
            if(employee == null){
                requestDispatcher = request.getRequestDispatcher("notexisted.html");
                requestDispatcher.forward(request, response);
            }else{
                out.println("<html>");
                out.println("<body>");
                out.println("<br><br><br>");
                out.println("<form method='post' action='update.do'>");
                out.println("<table style='margin-left:auto; margin-right:auto;'>");
                out.println("<tr><td>Employee Number : </td><td>"+employee.getEno()+"</td></tr>");
                out.println("<input type='hidden' name='eno' value='"+employee.getEno()+"'>");
                out.println("<tr><td>Employee Name</td><td><input type='text' name='ename' value='"+employee.getEname()+"'></td></tr>");
                out.println("<tr><td>Employee Salary</td><td><input type='text' name='esal' value='"+employee.getEsal()+"'></td></tr>");
                out.println("<tr><td>Employee Address</td><td><input type='text' name='eaddr' value='"+employee.getEaddr()+"'></td></tr>");
                out.println("<tr><td><input type='submit' value='UPDATE'></td></tr>");
                out.println("</table></form></body></html>");
            }
        }
        if(requestURI.endsWith("update.do")){
            eno = Integer.parseInt(request.getParameter("eno"));
            ename = request.getParameter("ename");
            esal = Float.parseFloat(request.getParameter("esal"));
            eaddr = request.getParameter("eaddr");
            employee = new Employee();
            employee.setEno(eno);
            employee.setEname(ename);
            employee.setEsal(esal);
            employee.setEaddr(eaddr);
            status = employeeService.updateEmployee(employee);
            if(status.equalsIgnoreCase("success")){
                requestDispatcher = request.getRequestDispatcher("success.html");
                requestDispatcher.forward(request, response);
            }
            if(status.equalsIgnoreCase("failure")){
                requestDispatcher = request.getRequestDispatcher("failure.html");
                requestDispatcher.forward(request, response);
            }
        }
        if(requestURI.endsWith("delete.do")){
            eno = Integer.parseInt(request.getParameter("eno"));
            employee = employeeService.searchEmployee(eno);
            if(employee == null){
                requestDispatcher = request.getRequestDispatcher("notexisted.html");
                requestDispatcher.forward(request, response);
            }else{
                status = employeeService.deleteEmployee(eno);
                if(status.equalsIgnoreCase("success")){
                    requestDispatcher = request.getRequestDispatcher("success.html");
                    requestDispatcher.forward(request, response);
                }
                if(status.equalsIgnoreCase("failure")){
                    requestDispatcher = request.getRequestDispatcher("failure.html");
                    requestDispatcher.forward(request, response);
                }
            }
        }


    }


    @Override
    public void destroy() {
        ConnectionFactory.close();
    }
}
