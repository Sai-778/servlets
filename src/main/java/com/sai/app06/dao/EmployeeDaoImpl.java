package com.sai.app06.dao;
import com.sai.app06.beans.Employee;
import com.sai.app06.factory.ConnectionFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class EmployeeDaoImpl implements EmployeeDao{


    @Override
    public String add(Employee employee) {
        String status = "";
        try{
            Connection connection = ConnectionFactory.getConnection();
            Employee employee1 = search(employee.getEno());
            if(employee1 == null){
                PreparedStatement preparedStatement = connection.prepareStatement("insert into emp1 values(?,?,?,?)");
                preparedStatement.setInt(1, employee.getEno());
                preparedStatement.setString(2, employee.getEname());
                preparedStatement.setFloat(3, employee.getEsal());
                preparedStatement.setString(4, employee.getEaddr());
                int rowCount = preparedStatement.executeUpdate();
                if(rowCount == 1){
                    status = "success";
                }else{
                    status = "failure";
                }


            }else{
                status = "existed";
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return status;
    }


    @Override
    public Employee search(int eno) {
        Employee employee = null;
        try{
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();;
            ResultSet resultSet = statement.executeQuery("select * from emp1 where ENO = "+eno);
            boolean b = resultSet.next();
            if(b == true){
                employee = new Employee();
                employee.setEno(resultSet.getInt("ENO"));
                employee.setEname(resultSet.getString("ENAME"));
                employee.setEsal(resultSet.getFloat("ESAL"));
                employee.setEaddr(resultSet.getString("EADDR"));
            }else{
                employee = null;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return employee;
    }


    @Override
    public String update(Employee employee) {
        String status = "";
        try{
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update emp1 set ENAME = ?, ESAL = ?, EADDR = ? where ENO = ?");
            preparedStatement.setString(1, employee.getEname());
            preparedStatement.setFloat(2, employee.getEsal());
            preparedStatement.setString(3, employee.getEaddr());
            preparedStatement.setInt(4, employee.getEno());
            int rowCount = preparedStatement.executeUpdate();
            if(rowCount == 1){
                status = "success";
            }else{
                status = "failure";
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return status;
    }


    @Override
    public String delete(int eno) {
        String status = "";
        try{
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from emp1 where ENO = ?");
            preparedStatement.setInt(1, eno);
            int rowCount = preparedStatement.executeUpdate();
            if(rowCount == 1){
                status = "success";
            }else{
                status = "failure";
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return status;
    }
}
