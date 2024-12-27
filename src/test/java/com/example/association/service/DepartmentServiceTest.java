/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.association.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.association.model.Department;
import com.example.association.model.Employee;
import com.example.association.repository.DepartmentRepo;
import com.example.association.repository.EmployeeRepo;

/**
 *
 * @author 10747968
 */

@SpringBootTest
@ActiveProfiles("test")
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDepartment(){
        List<Employee> employee = new ArrayList<>();
        Department department = new Department(1,"Java Full Stack", employee);
        when(departmentRepo.save(department)).thenReturn(department);

        Department createDept = departmentService.createDepartment(department);

        assertNotNull(createDept);
        verify(departmentRepo, times(1)).save(department);

    }

    @Test
    void testGetAllDepartment(){
        List<Employee> employee = new ArrayList<>();
        Department department1 = new Department(1,"Java Full Stack", employee);
        Department department2 = new Department(2,"Cyber", employee);

        when(departmentRepo.findAll()).thenReturn(Arrays.asList(department1, department2));

        List<Department> dept = departmentService.getAllDepartments();

        assertEquals(2, dept.size());
        verify(departmentRepo, times(1)).findAll();
    }

    @Test
    void testGetDepartmentByID(){
        List<Employee> employee = new ArrayList<>();
        Department department = new Department(1,"Java Full Stack", employee);

        when(departmentRepo.findById(1)).thenReturn(Optional.of(department));

        Optional<Department> dept = departmentService.getById(1);

        assertEquals(dept.get().getDepartment(), department.getDepartment());
        verify(departmentRepo, times(2)).findById(1);

    }

    @Test
    void testUpdateDepartment(){
        List<Employee> employee = new ArrayList<>();
        Department department = new Department(1,"Java Full Stack", employee);
        Department updateDepartment = new Department(1,"FullStack", new ArrayList<>());

        Employee employee1 = new Employee(department, 1, "rahul", 3000);
        Employee employee2 = new Employee(department, 2, "raj", 3500);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(departmentRepo.findById(1)).thenReturn(Optional.of(department));

        when(employeeRepo.findByDepartment_Department(department.getDepartment())).thenReturn(employees);
        when(departmentRepo.save(department)).thenReturn(updateDepartment);

        Department result = departmentService.updateDepartment(1, updateDepartment);

        assertNotNull(result);
        assertEquals("FullStack", result.getDepartment());

        verify(departmentRepo, times(1)).findById(1);
        verify(employeeRepo, times(1)).findByDepartment_Department("Java Full Stack");
        verify(departmentRepo, times(1)).save(department);
        verify(employeeRepo, times(2)).save(any(Employee.class));

    }

    @Test
    void testDeleteDepartment(){
        List<Employee> employee = new ArrayList<>();
        Department department = new Department(1,"Java Full Stack", employee);

        when(departmentRepo.findById(1)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1);

        verify(departmentRepo, times(1)).findById(1);
        verify(departmentRepo, times(1)).deleteById(1);
    }

}
