package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;


    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    //CRUD

    //Create
    @PostMapping
    ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        var employeeName = employee.getName();
        if(employeeName.isEmpty())
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        repository.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //R
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    List<Employee> getEmployee(@PathVariable Long id){
        if (repository.findById(id).isEmpty())
            return repository.findAll();
        return repository.findAllById(id);
    }

    //U
    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        var updatedEmployee = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAddress(employee.getAddress());
            updatedEmployee.setPhoneNumber(employee.getPhoneNumber());
            repository.save(updatedEmployee);
            return ResponseEntity.ok(updatedEmployee);
    }

    //D
    @DeleteMapping("/{id}")
    ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
        var toDelete = repository.findById(id);
        if (toDelete.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }






}
