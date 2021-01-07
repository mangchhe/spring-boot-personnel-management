package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.repository.EmployeeRepository;

import java.util.ArrayList;
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
    public List<EmployeeDTO.DuplicationName> viewByName(String name){
        List<EmployeeDTO.DuplicationName> list = new ArrayList<>();
        for(Employee e : employeeRepository.findAllByName(name)){
            list.add(
                    EmployeeDTO.DuplicationName.builder()
                            .emp_id(e.getEmp_id())
                            .emp_position(e.getEmp_position())
                            .emp_name(e.getEmp_name())
                            .emp_internal_num(e.getEmp_internal_num())
                            .emp_join_date(e.getEmp_join_date())
                            .dept_name(e.getDepartment().getDept_name())
                            .build()
            );
        }
        return list;
    }

}
