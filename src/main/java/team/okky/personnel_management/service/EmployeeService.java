package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * 이름 검색(동명이인)
     * @param name
     * @return 해당하는 이름만 담은 사원 목록
     */
    public List<Employee> viewByName(String name){
        return employeeRepository.findAllByName(name);
    }

}
