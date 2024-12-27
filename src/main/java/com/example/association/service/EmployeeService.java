/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.association.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.association.model.Department;
import com.example.association.model.Employee;
import com.example.association.repository.DepartmentRepo;
import com.example.association.repository.EmployeeRepo;

/**
 *
 * @author 10747968
 */

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    public Employee creatEmployee(Employee employee){
        Optional<Department> optional = departmentRepo.findByDepartment(employee.getDepartment().getDepartment());
        if(optional.isPresent()){
            employee.setDepartment(optional.get());
            return employeeRepo.save(employee);
        }
        else{
            throw new RuntimeException("Department not found");
        }
    }

    public List<Employee> getAllEmployee(){
        return employeeRepo.findAll();
    }

    public Optional<Employee> getById(int id){
        Optional<Employee> optional = employeeRepo.findById(id);
        if(optional.isPresent()){
            return employeeRepo.findById(id);
        }
        else
            throw new NullPointerException("Employee not found with ID: "+id);
    }

    public Employee updateEmployee(int id, Employee employee) {
        return employeeRepo.findById(id).map(emp -> {
            emp.setName(employee.getName());
            emp.setSalary(employee.getSalary());

            Optional<Department> optional = departmentRepo.findByDepartment(employee.getDepartment().getDepartment());
            if (optional.isPresent()) {
                emp.setDepartment(optional.get());
            } else {
                throw new RuntimeException("Department not found of Employee ID: " + id);
            }
            return employeeRepo.save(emp);
        }).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }


    public void deleteEmployee(int id){
        Optional<Employee> optional = employeeRepo.findById(id);
        if(optional.isPresent()){
            Employee emp = optional.get();
            Department dep = emp.getDepartment();
            dep.getEmployees().remove(emp);
            employeeRepo.delete(emp);
        }
        else
            throw new RuntimeException("Employee not found with ID: "+id);
    }

    public List<Employee> fetchEmployeeByDepartmentId(int departmentId){
        return employeeRepo.findEmployeesByDepartmentId(departmentId);
    }

}
