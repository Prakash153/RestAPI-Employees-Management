package com.codingshuttle.SpringBootWebTutorial.controllers;

import com.codingshuttle.SpringBootWebTutorial.dto.DepartmentDTO;
import com.codingshuttle.SpringBootWebTutorial.entities.DepartmentEntity;
import com.codingshuttle.SpringBootWebTutorial.repositaries.DepartmentRepository;
import com.codingshuttle.SpringBootWebTutorial.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/departments")
public class DepartmentController {


    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public DepartmentDTO getDepartmentById(@PathVariable(name = "departmentId") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentInput) {
        return departmentService.createDepartment(departmentInput);
    }

    @PutMapping("/{departmentId}")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO departmentDTO, @PathVariable(name = "departmentId") Long id) {
        return departmentService.updateDepartment(departmentDTO, id);
    }
    @PatchMapping("/{departmentId}")
    public DepartmentDTO updateDepartmentPartially(@RequestBody Map<String , Object> updates, @PathVariable(name = "departmentId") Long id){
        return departmentService.updateDepartmentPartially(updates, id);
    }
    @DeleteMapping("/departmentId")
    public boolean deleteDepartmentById(@PathVariable (name = "departmentId") Long id){
        return departmentService.deleteDepartmentById(id);
    }

}
