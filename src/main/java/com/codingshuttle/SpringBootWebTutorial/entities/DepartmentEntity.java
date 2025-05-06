package com.codingshuttle.SpringBootWebTutorial.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "departments")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;
    private String title;
    private boolean isActive ;
    private LocalDate createdAt;

}
