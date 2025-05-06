package com.codingshuttle.SpringBootWebTutorial.controllers;

import com.codingshuttle.SpringBootWebTutorial.dto.EmployeeDTO;
import com.codingshuttle.SpringBootWebTutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.SpringBootWebTutorial.services.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
//1. To remove "/employees" from path and also let the dispatcher servlett know about the url u can replace it by using RequestMapping on the top
@RequestMapping("/employees")  // parent
public class EmployeeController {
//    @GetMapping(path="/getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret Message";
//    }

    // we can connect controller class directly to repository class but it is not good practice so we use service layer in between
//private final EmployeeRepository employeeRepository;
//
//    public EmployeeController(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    @Autowired
    private  EmployeeService employeeService;





    //Path variable method

    // 2. here we can also change the name of pathvariable that is different from the path present in getMapping field by adding
    // name attribute to pathvariable
//    // 3. same thing can be done in request param also
//    @GetMapping(path = "/{employeeId}") //child
//    public EmployeeDTO getEmployeeId(@PathVariable (name = "employeeId")Long id) {
//        return new EmployeeDTO(id, "Prakash", "Prakash@gmail.com", 23, LocalDate.of(2024, 9, 16), true);
//    }
    @GetMapping(path = "/{employeeId}") //child
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "employeeId") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found with id " + id));
    }
    // Request param method :
    //URL: localhost:9000/employees?age=12
    //3.  usijng (required false to make that param optional
    @GetMapping  //child
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    // RequestBody is used to pass long(large) form of data
    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid  EmployeeDTO inputEmployee) {
        EmployeeDTO saveEmployee =  employeeService.saveEmployee(inputEmployee);
        return new ResponseEntity<>(saveEmployee,HttpStatus.CREATED);
    }

//    public String ceateNewEmployee(){
//        return "Hi from Post";
//    }


    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }


    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
         boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
         if(gotDeleted) return ResponseEntity.ok(true);
         return ResponseEntity.notFound().build();
    }

    @PatchMapping (path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO =  employeeService.updatePartialEmployeeById(employeeId, updates);
    if(employeeDTO == null)
        return ResponseEntity.notFound().build();
    return ResponseEntity.ok(employeeDTO);
    }

}
