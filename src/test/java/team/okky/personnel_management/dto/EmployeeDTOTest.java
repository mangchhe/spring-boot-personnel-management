package team.okky.personnel_management.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;

import java.time.LocalDate;

@SpringBootTest
class EmployeeDTOTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void ListIndex() throws Exception {
        //given
        Employee employee = Employee.builder()
                .empName("테스터")
                .empJoinDate(LocalDate.now())
                .department(Department.builder()
                        .deptName("부서1")
                        .build())
                .empInternalNum("E-0001")
                .empPhoneNum("010-1234-5678")
                .empPosition("팀장")
                .build();
        //when
        EmployeeDTO.ListIndex map = modelMapper.map(employee, EmployeeDTO.ListIndex.class);
        //then
        Assertions.assertEquals(map.getEmpName(), employee.getEmpName());
        Assertions.assertEquals(map.getEmpJoinDate(), employee.getEmpJoinDate());
        Assertions.assertEquals(map.getDepartmentDeptName(), employee.getDepartment().getDeptName());
        Assertions.assertEquals(map.getEmpInternalNum(), employee.getEmpInternalNum());
        Assertions.assertEquals(map.getEmpPhoneNum(), employee.getEmpPhoneNum());
        Assertions.assertEquals(map.getEmpPosition(), employee.getEmpPosition());
    }


}