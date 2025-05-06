package com.codingshuttle.SpringBootWebTutorial.services;

import com.codingshuttle.SpringBootWebTutorial.repositaries.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;


}
