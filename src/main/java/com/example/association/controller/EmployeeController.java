/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.association.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.association.model.Employee;
import com.example.association.service.EmployeeService;

/**
 *
 * @author 10747968
 */

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        return ResponseEntity.status(201).body(employeeService.creatEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployee(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        return ResponseEntity.status(200).body(employeeService.getById(id));
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee){
        return ResponseEntity.status(200).body(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(204).body("Employee deleted successfully with ID: "+id);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> fetchEmployeeByDepartmentId(@PathVariable int id){
        return ResponseEntity.status(200).body(employeeService.fetchEmployeeByDepartmentId(id));
    }

}
