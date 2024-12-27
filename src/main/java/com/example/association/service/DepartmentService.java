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
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public Department createDepartment(Department department){
        return departmentRepo.save(department);
    }

    public List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }

    public Optional<Department> getById(int id){
        Optional<Department> optional = departmentRepo.findById(id);
        if(optional.isPresent()){
            return departmentRepo.findById(id);
        }else{
            throw new NullPointerException("Department not found with ID: "+id);
        }
    }

    public Department updateDepartment(int id, Department department) {
        Optional<Department> optional = departmentRepo.findById(id);
        if (optional.isPresent()) {
            Department existDepartment = optional.get();

            List<Employee> employees = employeeRepo.findByDepartment_Department(existDepartment.getDepartment());
    
            existDepartment.setDepartment(department.getDepartment());
            Department updatedDepartment = departmentRepo.save(existDepartment);
    
            employees.stream()
                     .peek(emp -> emp.setDepartment(updatedDepartment))
                     .forEach(employeeRepo::save);
    
            return updatedDepartment;
        } else {
            throw new NullPointerException("Department not found with ID: " + id);
        }
    }

    public void deleteDepartment(int id){
        Optional<Department> optional = departmentRepo.findById(id);
        if(optional.isPresent()){
            departmentRepo.deleteById(id);
        }
        else{
            throw new NullPointerException("Department not found with ID: "+id);
        }
    }

}
