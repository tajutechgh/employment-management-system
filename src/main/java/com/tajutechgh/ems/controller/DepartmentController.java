package com.tajutechgh.ems.controller;

import com.tajutechgh.ems.dto.DepartmentDto;
import com.tajutechgh.ems.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

   //TODO: Create department
    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){

        DepartmentDto savedDepartment = departmentService.createDepartment(departmentDto);

        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    //TODO: Get all departments
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){

        List<DepartmentDto> departments = departmentService.getAllDepartments();

        return ResponseEntity.ok(departments);
    }

    //TODO: Get department by id
    @GetMapping("/get/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId){

        DepartmentDto departmentDto = departmentService.getDepartmentById(departmentId);

        return ResponseEntity.ok(departmentDto);
    }

    //TODO: Update department by id
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody DepartmentDto updatedDepartment){

        DepartmentDto departmentDto = departmentService.updateDepartment(departmentId, updatedDepartment);

        return ResponseEntity.ok(departmentDto);
    }

    //TODO: Delete department by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId){

        departmentService.deleteDepartment(departmentId);

        return ResponseEntity.ok("Department deleted successfully!.");
    }
}
