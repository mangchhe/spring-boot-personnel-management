package team.okky.personnel_management.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void 전체_직원_목록() throws Exception {
        //given
        for (int i = 0; i < 105; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .build();
            employeeRepository.save(employee);
        }
        //when
        List<EmployeeDTO.ListIndex> result = employeeService.viewAll(new PageRequestDTO(11));
        //then
        assertThat(105).isEqualTo(employeeRepository.findAllOrderByJoinDateTotal());
        assertThat(5).isEqualTo(result.size());

    }

    @Test
    public void 직원_이름_검색() throws Exception {
        //given
        List<String> findNameList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터")
                    .build();
            Employee employee2 = Employee.builder()
                    .empName("테스터" + i)
                    .build();
            findNameList.add("테스터");
            employeeRepository.save(employee);
            employeeRepository.save(employee2);
        }
        //when
        List<EmployeeDTO.ListIndex> result = employeeService.viewAllByName("테스터", new PageRequestDTO(1));
        //then
        assertThat(result).extracting("empName")
                .containsAll(findNameList);

        assertThat(findNameList.size()).isEqualTo(result.size());
    }
    
    @Test
    public void 직원_부서_검색() throws Exception {
        //given
        List<String> findDeptNameList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employee employee = Employee.builder()
                    .department(
                            Department.builder()
                            .deptName("인사과")
                            .build())
                    .build();
            Employee employee2 = Employee.builder()
                    .department(
                            Department.builder()
                                    .deptName("인사과 + i")
                                    .build()
                    )
                    .build();
            findDeptNameList.add("인사과");
            employeeRepository.save(employee);
            employeeRepository.save(employee2);
        }
        //when
        List<EmployeeDTO.ListIndex> result = employeeService.viewAllByDept("인사과", new PageRequestDTO(1));
        //then
        assertThat(result).extracting("departmentDeptName")
                .containsAll(findDeptNameList);
        assertThat(result.size()).isEqualTo(findDeptNameList.size());
    }

    @Test
    public void 사원_등록() throws Exception {
        //given
        Department department = Department.builder()
                .deptName("인사과")
                .build();
        departmentRepository.save(department);
        //when
        employeeService.createEmployee(EmployeeDTO.AddEmployee.builder()
                .empName("테스터")
                .deptId(department.getDeptId())
                .build());
        //then
        assertThat(employeeRepository.findAllByName("테스터", new PageRequestDTO(1))).isNotEmpty();
    }
    
    @Test
    public void 사원_정보_변경() throws Exception {
        //given
        Employee employee = Employee.builder()
                .empName("테스터")
                .empPhoneNum("010-1234-5678")
                .empJoinDate(LocalDate.now().minusDays(1))
                .build();
        employeeRepository.save(employee);
        //when, then
        assertThat("010-1234-5678").isEqualTo(employee.getEmpPhoneNum());
        assertThat(LocalDate.now().minusDays(1)).isEqualTo(employee.getEmpJoinDate());
        employeeService.updateEmployee(
                EmployeeDTO.UpdateEmployee.builder()
                        .empId(employee.getEmpId())
                        .empPhoneNum("010-5678-1234")
                        .empJoinDate(LocalDate.now())
                        .build()
        );
        assertThat("010-5678-1234").as("핸드폰 번호가 맞지 않습니다")
                .isEqualTo(employee.getEmpPhoneNum());
        assertThat(LocalDate.now()).as("입사 날짜가 맞지 않습니다")
                .isEqualTo(employee.getEmpJoinDate());
    }
}