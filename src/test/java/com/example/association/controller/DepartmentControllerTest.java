package com.example.association.controller;

import java.util.ArrayList;
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

import com.example.association.model.Department;
import com.example.association.service.DepartmentService;

public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = new Department(1, "Java FullStack", new ArrayList<>());

        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/api/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"department\":\"Java FullStack\",\"employees\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.department").value("Java FullStack"))
                .andExpect(jsonPath("$.id").value(1));

        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }

    @Test
    void testGetAllDepartment() throws Exception{
        Department department1 = new Department(1, "Java FullStack", new ArrayList<>());
        Department department2 = new Department(2, "Cyber", new ArrayList<>());

        List<Department> dept = Arrays.asList(department1, department2);

        when(departmentService.getAllDepartments()).thenReturn(dept);

        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].department").value("Java FullStack"));

        verify(departmentService, times(1)).getAllDepartments();        
    }


    @Test
    void testGetDepartmentById() throws Exception{
        Department department = new Department(1, "Java FullStack", new ArrayList<>());

        when(departmentService.getById(1)).thenReturn(Optional.of(department));

        mockMvc.perform(get("/api/department/1"))
                .andExpect (status().isOk())
                .andExpect(jsonPath("$.department").value(department.getDepartment()));

        verify(departmentService, times(1)).getById(1);
    }

    @Test
    void testUpdateDepartment() throws Exception{
        Department department = new Department(1, "Java FullStack", new ArrayList<>());
        Department updateDepartment = new Department(1, "FullStack", new ArrayList<>());

        when(departmentService.updateDepartment(eq(1),any(Department.class))).thenReturn(updateDepartment);

        mockMvc.perform(put("/api/department/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"department\":\"FullStack\",\"employees\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.department").value("FullStack"))
                .andExpect(jsonPath("$.id").value(1));

        verify(departmentService, times(1)).updateDepartment(eq(1), any(Department.class));
    }

    @Test
    void testDeleteDepartment() throws Exception{
        Department department = new Department(1, "Java FullStack", new ArrayList<>());

        doNothing().when(departmentService).deleteDepartment(1);

        mockMvc.perform(delete("/api/department/1"))
                .andExpect(status().isNoContent());

        verify(departmentService, times(1)).deleteDepartment(1);
    }
}