package com.sai.app06.factory;


import com.sai.app06.dao.EmployeeDao;
import com.sai.app06.dao.EmployeeDaoImpl;


public class EmployeeDaoFactory {
    private static EmployeeDao employeeDao;
    static {
        employeeDao = new EmployeeDaoImpl();
    }


    public static EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
}

