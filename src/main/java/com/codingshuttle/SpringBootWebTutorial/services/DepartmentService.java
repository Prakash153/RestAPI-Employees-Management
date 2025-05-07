package com.codingshuttle.SpringBootWebTutorial.services;

import com.codingshuttle.SpringBootWebTutorial.dto.DepartmentDTO;
import com.codingshuttle.SpringBootWebTutorial.entities.DepartmentEntity;
import com.codingshuttle.SpringBootWebTutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.SpringBootWebTutorial.repositaries.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public DepartmentDTO getDepartmentById(Long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id).orElse(null);
        return modelMapper.map(departmentEntity, DepartmentDTO.class);

    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        return departmentEntityList
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentInput) {

        DepartmentEntity departmentEntitySave = modelMapper.map(departmentInput, DepartmentEntity.class);
        DepartmentEntity departmentEntity = departmentRepository.save(departmentEntitySave);
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long id) {
        checkDepartmentExist(id);
        DepartmentEntity departmentEntityUpdate = modelMapper.map(departmentDTO, DepartmentEntity.class);
        departmentEntityUpdate.setId(id);
        DepartmentEntity departmentEntity = departmentRepository.save(departmentEntityUpdate);
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public void checkDepartmentExist(Long departmentId) {
        boolean exist = departmentRepository.existsById(departmentId);
        if (!exist) throw new ResourceNotFoundException("Department Not found with id: " + departmentId);

    }

    public DepartmentDTO updateDepartmentPartially(Map<String, Object> updates, Long id) {
        checkDepartmentExist(id);
        DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(DepartmentEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, departmentEntity, value);
        });

        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public boolean deleteDepartmentById(Long id) {
        checkDepartmentExist(id);
         departmentRepository.deleteById(id);
         return true;
    }
}
