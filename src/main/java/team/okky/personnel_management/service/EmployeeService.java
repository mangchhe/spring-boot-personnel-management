package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    /**
     * 전체 직원 목록 보여주기
     * @detail 직원 입사일 기준으로 내림차순
     * @return 직원 정보가 담긴 목록
     */
    public List<EmployeeDTO.ListIndex> viewAll(){
        return employeeRepository.findAllOrderByJoinDate().stream()
                .map(m -> modelMapper.map(m, EmployeeDTO.ListIndex.class))
                .collect(Collectors.toList());
    }

    /**
     * 이름 검색
     * @param name
     * @return 해당하는 이름만 담은 사원 목록, 동명이인 포함
     */
    public List<EmployeeDTO.ListIndex> viewAllByName(String name){
        return employeeRepository.findAllByName(name).stream()
                .map(m -> modelMapper.map(m, EmployeeDTO.ListIndex.class))
                .collect(Collectors.toList());
    }

    /**
     * 직원 부서로 검색
     * @param deptName
     * @return 해당 부서를 검색한 직원 목록
     */
    public List<EmployeeDTO.ListIndex> viewAllByDept(String deptName){
        return employeeRepository.findAllByDept(deptName).stream()
                .map(m -> modelMapper.map(m, EmployeeDTO.ListIndex.class))
                .collect(Collectors.toList());
    }

    /**
     * 사원 등록
     * @param addEmployee
     * @return 등록된 사원
     */
    @Transactional(readOnly = false)
    public Employee createEmployee(EmployeeDTO.AddEmployee addEmployee){
        Employee employee = Employee.builder()
                .empName(addEmployee.getEmpName())
                .department(departmentRepository.findOne(addEmployee.getDeptId()))
                .empPosition(addEmployee.getEmpPosition())
                .empJoinDate(addEmployee.getEmpJoinDate())
                .empPhoneNum(addEmployee.getEmpPhoneNum())
                .build();
        employeeRepository.save(employee);
        return employee;
    }

    /**
     * 사원 정보 변경
     * @param updateEmployee
     * @return 변경된 사원
     */
    @Transactional(readOnly = false)
    public Employee updateEmployee(EmployeeDTO.UpdateEmployee updateEmployee){
        Employee employee = employeeRepository.findOne(updateEmployee.getEmpId());
        employee.changePhoneNum(updateEmployee.getEmpPhoneNum());
        employee.changeJoinDate(updateEmployee.getEmpJoinDate());
        return employee;
    }

}
