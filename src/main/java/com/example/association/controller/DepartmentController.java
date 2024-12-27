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

import com.example.association.model.Department;
import com.example.association.service.DepartmentService;

/**
 *
 * @author 10747968
 */

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department){
        return ResponseEntity.status(201).body(departmentService.createDepartment(department));
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartment(){
        return ResponseEntity.status(200).body(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable int id){
        return ResponseEntity.status(200).body(departmentService.getById(id));
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id,@RequestBody Department department){
        return ResponseEntity.status(200).body(departmentService.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.status(204).body("Department deleted successfull with ID: "+id);
    }

}
