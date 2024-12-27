/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.association.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.association.model.Employee;

/**
 *
 * @author 10747968
 */

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartment_Department(String departmentName);
    
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    List<Employee> findEmployeesByDepartmentId(@Param("departmentId") int departmentId);

}
