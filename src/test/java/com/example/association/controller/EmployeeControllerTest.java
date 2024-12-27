/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.association.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.association.model.Employee;
import com.example.association.service.EmployeeService;

/**
 *
 * @author 10747968
 */
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testCreateEmployee() throws Exception{
        Employee employee = new Employee(null, 1, "rahul", 30000);

        when(employeeService.creatEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"rahul\",\"salary\":30000,\"department\":null}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));

        verify(employeeService, times(1)).creatEmployee(any(Employee.class));
    }

    @Test
    void testGetAllEmployee() throws Exception{
        Employee employee1 = new Employee(null, 1, "rahul", 30000);
        Employee employee2 = new Employee(null, 2, "raj", 20000);

        List<Employee> emp = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployee()).thenReturn(emp);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("rahul"))
                .andExpect(jsonPath("$[1].salary").value(20000));

        verify(employeeService, times(1)).getAllEmployee();

    }

    @Test
    void testGetEmployeeById() throws Exception{

        Employee employee = new Employee(null, 1, "rahul", 30000);

        when(employeeService.getById(1)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("rahul"));

        verify(employeeService, times(1)).getById(1);
    }

    @Test
    void testUpdateEmployee() throws Exception{

        Employee employee = new Employee(null, 1, "rahul", 30000);
        Employee updateEmployee = new Employee(null, 1, "Rahul Kumar", 35000);

        when(employeeService.updateEmployee(eq(1), any(Employee.class))).thenReturn(updateEmployee);

        mockMvc.perform(put("/api/employee/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Rahul Kumar\",\"salary\":35000,\"department\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(updateEmployee.getName()))
                .andExpect(jsonPath("$.salary").value(updateEmployee.getSalary()));

        verify(employeeService, times(1)).updateEmployee(eq(1),any(Employee.class));
    }

    @Test
    void testDeleteEmployee() throws Exception{
        Employee employee = new Employee(null, 1, "rahul", 30000);

        doNothing().when(employeeService).deleteEmployee(1);

        mockMvc.perform(delete("/api/employee/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1);
    }

}
