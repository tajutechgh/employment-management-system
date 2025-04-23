package com.tajutechgh.ems.service.implementation;

import com.tajutechgh.ems.dto.DepartmentDto;
import com.tajutechgh.ems.entity.Department;
import com.tajutechgh.ems.exception.ResourceNotFoundException;
import com.tajutechgh.ems.mapper.DepartmentMapper;
import com.tajutechgh.ems.repository.DepartmentRepository;
import com.tajutechgh.ems.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImplementation(DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = DepartmentMapper.mapToDepartment(departmentDto);

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(

                () -> new ResourceNotFoundException("Department", "id", departmentId)
        );

        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        return departments.stream().map(

                (department) -> DepartmentMapper.mapToDepartmentDto(department)).collect(Collectors.toList()
        );
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(

                () -> new ResourceNotFoundException("Department", "id", departmentId)
        );

        department.setDepartmentName(updatedDepartment.getDepartmentName());
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(

                () -> new ResourceNotFoundException("Department", "id", departmentId)
        );

        departmentRepository.delete(department);
    }
}
