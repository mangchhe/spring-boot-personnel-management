package team.okky.personnel_management.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeePosition;
import team.okky.personnel_management.evaluation.Evaluation;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.salary.SalaryDTO;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.evaluation.EvaluationRepository;
import team.okky.personnel_management.salary.SalaryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class SalaryServiceTest {
    @Autowired
    SalaryService salaryService;
    @Autowired EmployeeRepository employeeRepository;
    @Autowired EvaluationRepository evaluationRepository;

    @BeforeEach
    public void init(){
        String[] position = new String[]{"대리", "사원", "부장", "본부장", "사장", "차장", "과장"};
        String[] comment = new String[]{"BEST","SOSO","BAD"};
        List<Employee> employeeList = new ArrayList<>();
        int score = 0;

        for (int i = 0; i < 50; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .empPosition(EmployeePosition.findByEmployeePosition(position[i % 7]))
                    .department(Department.builder()
                            .deptName("부서" + i)
                            .build())
                    .build();
            employeeList.add(employee);
            employeeRepository.save(employee);

            evaluationRepository.save(Evaluation.builder()
                    .evalResultScore(score)
                    .employee(employeeList.get(i))
                    .build());
        }
    }

    @Test
    public void 급여목록_전체조회(){
        //when
        List<SalaryDTO.indexSalary> result = salaryService.viewAll(new PageRequestDTO(1));
        //then
        Assertions.assertThat(10).isEqualTo(result.size());
        List<SalaryDTO.indexSalary> salaryPerEmpPosition = result.stream().filter(t->t.getEmpPosition().equals("사원"))
                .collect(Collectors.toList());
        Assertions.assertThat(3000).isEqualTo(salaryPerEmpPosition.get(0).getSalary());
    }

    @Test
    public void 급여관리_이름검색(){
        //when
        List<SalaryDTO.indexSalary> result = salaryService.viewAllByName("테스터33",new PageRequestDTO(1));
        List<SalaryDTO.indexSalary> result2 = salaryService.findByName("테스터33");
        //then
        Assertions.assertThat("테스터33").isEqualTo(result.get(0).getEmpName());
        Assertions.assertThat(result.get(0).getSalary()).isEqualTo(result2.get(0).getSalary());
    }

    @Test
    public void 급여_수정(){
        //given
        List<SalaryDTO.indexSalary> beforeUpdate = salaryService.findByName("테스터33");
        SalaryDTO.updateForm salaryPerEmp = SalaryDTO.updateForm.builder()
                .empId(beforeUpdate.get(0).getEmpId())
                .salary(beforeUpdate.get(0).getSalary()+500)
                .incentive(beforeUpdate.get(0).getIncentive()+50)
                .build();
        //when
        salaryService.update(salaryPerEmp);
        List<SalaryDTO.indexSalary> afterUpdate = salaryService.findByName("테스터33");
        //then
        Assertions.assertThat(beforeUpdate.get(0).getSalary()).isNotEqualTo(afterUpdate.get(0).getSalary());
        Assertions.assertThat(beforeUpdate.get(0).getIncentive()).isNotEqualTo(afterUpdate.get(0).getIncentive());
    }

}
