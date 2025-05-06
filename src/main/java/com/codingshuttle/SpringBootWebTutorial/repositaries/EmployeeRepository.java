package com.codingshuttle.SpringBootWebTutorial.repositaries;

import com.codingshuttle.SpringBootWebTutorial.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    // we don't need to define any CRUD operations , search , delete , dave , update and all
    // everything will be handled by JPArepository

    // this method is also not required to be defined
    // it will be handled by JPAREPO
    List<EmployeeEntity> findByName(String name);
}
