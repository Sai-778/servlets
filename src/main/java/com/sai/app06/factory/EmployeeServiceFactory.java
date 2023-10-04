package com.sai.app06.factory;

import com.sai.app06.service.EmployeeService;
import com.sai.app06.service.EmployeeServiceImpl;


public class EmployeeServiceFactory {
    private static EmployeeService employeeService;
    static{
        employeeService = new EmployeeServiceImpl();
    }


    public static EmployeeService getEmployeeService() {
        return employeeService;
    }
}
