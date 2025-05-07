package com.codingshuttle.SpringBootWebTutorial.services;


import com.codingshuttle.SpringBootWebTutorial.dto.EmployeeDTO;
import com.codingshuttle.SpringBootWebTutorial.entities.EmployeeEntity;
import com.codingshuttle.SpringBootWebTutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.SpringBootWebTutorial.repositaries.EmployeeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public void checkEmployeeExist(Long employeeId) {
        boolean exist = employeeRepository.existsById(employeeId);
        if(!exist)   throw new ResourceNotFoundException("Employee Not found with id: "+ employeeId);

    }
    // we are using model mapper maven dependency to convert one class object to other
    // that is employee entity to employeeDTO


    public Optional<EmployeeDTO > getEmployeeById(Long id) {
//       Optional <EmployeeEntity employeeEntity = employeeRepository.findById(id) ;
        // since we do not want to create object again ,and again we would inject a bean from config folder,
//        ModelMapper mapper = new ModelMapper();
        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
//        since it is a list we need a stream library to convert each entity to dto
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());

    }

    public EmployeeDTO saveEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
      checkEmployeeExist(employeeId);

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity saveEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }



    public boolean deleteEmployeeById(Long employeeId) {
        checkEmployeeExist(employeeId);

        employeeRepository.deleteById(employeeId);
        return true;
    }

    // 1. we have to update all the field of key value pairs of the updates args in this method
    // 2. for that we can use the concept of reflection in java springboot
    // 3. reflection is concept inside java where you can directly go to object and can update the fields of that object
    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        checkEmployeeExist(employeeId);

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field,value) ->{
             Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
       // because fields are private
             fieldToBeUpdated.setAccessible(true);
       ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

        return modelMapper.map( employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
