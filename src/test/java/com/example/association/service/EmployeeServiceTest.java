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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee = new Employee(department, 1, "rahul",3000);

        when(departmentRepo.findByDepartment(department.getDepartment())).thenReturn(Optional.of(department));
        when(employeeRepo.save(employee)).thenReturn(employee);

        Employee createEmp = employeeService.creatEmployee(employee);
        
        assertNotNull(createEmp);
        verify(departmentRepo, times(1)).findByDepartment(department.getDepartment());
        verify(employeeRepo, times(1)).save(employee);

    }

    @Test
    void testGetAllEmployee(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee1 = new Employee(department, 1, "rahul",3000);
        Employee employee2 = new Employee(department, 1, "raj",3500);

        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        employeeService.getAllEmployee();

        assertEquals(2, employeeService.getAllEmployee().size());
        verify(employeeRepo, times(2)).findAll();
    }

    @Test
    void testGetEmployeeById(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee = new Employee(department, 1, "rahul",3000);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> emp = employeeService.getById(1);

        assertTrue(emp.isPresent());
        assertEquals(3000, employee.getSalary());
        verify(employeeRepo, times(2)).findById(1);
    }

    @Test
    void testUpdateEmployee(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee = new Employee(department, 1, "rahul",3000);
        Employee UpdateEmployee = new Employee(department, 1, "Rahul Kumar",3500);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(departmentRepo.findByDepartment(department.getDepartment())).thenReturn(Optional.of(department));
        when(employeeRepo.save(employee)).thenReturn(employee);

        Employee result = employeeService.updateEmployee(1, UpdateEmployee);

        assertEquals(result.getId(), employee.getId());
        assertEquals(result.getDepartment(), employee.getDepartment());
        assertEquals(result.getName(), employee.getName());

        verify(employeeRepo, times(1)).findById(1);
        verify(departmentRepo, times(1)).findByDepartment(department.getDepartment());
        verify(employeeRepo, times(1)).save(employee);
    }


    @Test
    void testDeleteEmployee(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee = new Employee(department, 1, "rahul",3000);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1);

        verify(employeeRepo, times(1)).findById(1);
        verify(employeeRepo, times(1)).delete(employee);
    }

    @Test
    public void testFetchEmployeeByDepartmentId(){
        Department department = new Department(1,"Java Full Stack", new ArrayList<>());
        Employee employee1 = new Employee(department, 1, "rahul",3000);
        Employee employee2 = new Employee(department, 2, "raj",2000);

        List<Employee> list = Arrays.asList(employee1,employee2);

        when(employeeRepo.findEmployeesByDepartmentId(department.getId())).thenReturn(list);

        List<Employee> result = employeeService.fetchEmployeeByDepartmentId(1);

        assertEquals(2, result.size());
        assertEquals("rahul", result.get(0).getName());
        assertEquals("raj", result.get(1).getName());

        verify(employeeRepo, times(1)).findEmployeesByDepartmentId(1);

    }


}
