package com.codingshuttle.SpringBootWebTutorial.dto;

import lombok.Data;


import java.time.LocalDate;


@Data
public class DepartmentDTO {
    private Long id ;
    private String title;
    private boolean isActive ;
    private LocalDate createdAt;

}
