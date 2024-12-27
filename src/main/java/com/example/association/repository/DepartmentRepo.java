/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.association.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.association.model.Department;

/**
 *
 * @author 10747968
 */

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    Optional<Department> findByDepartment(String department);

}
