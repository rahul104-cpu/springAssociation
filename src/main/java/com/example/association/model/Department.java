/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.association.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 *
 * @author 10747968
 */

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String department;

    @OneToMany(mappedBy="department", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Employee> employees;

    public Department() {
    }

    public Department(int id, String department, List<Employee> employees) {
        this.id = id;
        this.department = department;
        this.employees = employees;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


}
