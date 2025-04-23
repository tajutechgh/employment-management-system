package com.tajutechgh.ems.mapper;

import com.tajutechgh.ems.dto.DepartmentDto;
import com.tajutechgh.ems.entity.Department;

public class DepartmentMapper {

      public static Department mapToDepartment(DepartmentDto departmentDto){
        return new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription()
        );
    }

    public static DepartmentDto mapToDepartmentDto(Department department){
        return new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription()
        );
    }
}
