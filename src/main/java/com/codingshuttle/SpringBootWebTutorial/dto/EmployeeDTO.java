package com.codingshuttle.SpringBootWebTutorial.dto;

import com.codingshuttle.SpringBootWebTutorial.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
// this is basically a POJO class (Plain old Java Object)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotEmpty(message = "name field cannot be empty")
    @NotNull (message = "Required field in Employee: name")
    @Size (min = 3, max = 10 , message = "range of letters is between 3 to 10 ")
    private String name;
    @Email(message = "Email should be a valid email")
    private String email;
    @Max (value = 80 , message = "age cannot be greater than 80")
    @Min (value = 18, message = "age cannot be less than 18 ")
    private Integer age;

//    @Pattern (regexp = "^(ADMIN|USER)$", message = "the role of employee can be USER or ADMIN")
// pattern annotations custom annotation
    @EmployeeRoleValidation
    private String role;
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private Boolean isActive;


}
