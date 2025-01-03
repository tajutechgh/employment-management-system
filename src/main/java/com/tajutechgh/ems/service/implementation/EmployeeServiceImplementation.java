package com.tajutechgh.ems.service.implementation;

import com.tajutechgh.ems.dto.EmployeeDto;
import com.tajutechgh.ems.entity.Employee;
import com.tajutechgh.ems.exception.ResourceNotFoundException;
import com.tajutechgh.ems.mapper.EmployeeMapper;
import com.tajutechgh.ems.repository.EmployeeRepository;
import com.tajutechgh.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(

                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(

                (employee) -> EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList()
        );
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(

                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);

    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(

                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        employeeRepository.deleteById(employeeId);
    }
}
