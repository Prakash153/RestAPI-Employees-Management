package com.codingshuttle.SpringBootWebTutorial.controllers;

import com.codingshuttle.SpringBootWebTutorial.dto.DepartmentDTO;
import com.codingshuttle.SpringBootWebTutorial.entities.DepartmentEntity;
import com.codingshuttle.SpringBootWebTutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.SpringBootWebTutorial.repositaries.DepartmentRepository;
import com.codingshuttle.SpringBootWebTutorial.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/departments")
public class DepartmentController {


    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name = "departmentId") Long id) {
        Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentById(id);
        return departmentDTO.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentInput) {
        DepartmentDTO departmentDTO = departmentService.createDepartment(departmentInput);
        return new ResponseEntity<>(departmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO, @PathVariable(name = "departmentId") Long id) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentDTO, id));
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentPartially(@RequestBody Map<String, Object> updates, @PathVariable(name = "departmentId") Long id) {
        DepartmentDTO departmentDTO = departmentService.updateDepartmentPartially(updates, id);
        if (departmentDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(departmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable(name = "departmentId") Long id) {
        boolean gotDeleted = departmentService.deleteDepartmentById(id);
        if (gotDeleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();

    }

}
