package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void 이름검색_동명이인() throws Exception {
        //given
        List<EmployeeDTO.DuplicationName> employeeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employee employee = Employee.builder()
                    .emp_name("테스터")
                    .department(Department.builder()
                            .dept_name("인사과")
                            .build())
                    .build();
            employeeRepository.save(employee);
            EmployeeDTO.DuplicationName duplicationName = EmployeeDTO.DuplicationName.builder()
                    .empName("테스터")
                    .deptName("인사과")
                    .empId(employee.getEmp_id())
                    .build();
            employeeList.add(duplicationName);
        }
        for (int i = 0; i < 2; i++) {
            employeeRepository.save(
                    Employee.builder()
                            .emp_name("테스터" + i)
                            .build()
            );
        }
        //when, then
        if(!employeeService.viewByName("테스터").equals(employeeList)){
            Assertions.fail("동명이인 출력에 문제가 있습니다.");
        }
    }
}