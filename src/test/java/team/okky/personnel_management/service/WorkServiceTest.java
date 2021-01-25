package team.okky.personnel_management.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.EvaluationRepository;
import team.okky.personnel_management.repository.WorkRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class WorkServiceTest {
    @Autowired private WorkService workService;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private WorkRepository workRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EvaluationRepository evaluationRepository;

    List<Department> departmentList = new ArrayList<>();
    List<Work> workList = new ArrayList<>();
    List<Employee> employeeList = new ArrayList<>();
    int workSize = 10;
    @BeforeEach
    public void init() throws Exception {

        for (int i = 1; i < 6; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            departmentList.add(department);
        }


        String[] comment = new String[]{"BEST", "SOSO", "BAD"};
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Work work =
                    Work.builder()
                            .workName("업무" + i)
                            .workChargeName("담당자" + i)
                            .workStartDate(LocalDate.of(2020, 12, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 10 + i))
                            .department(departmentList.get(i / 2))
                            .build();
            workRepository.save(work);
            workList.add(work);
        }

        int j = -1;
        for (int i = 0; i < 50; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .department(
                            departmentList.get(i / 10)
                    )
                    .work(workList.get(i / 5))
                    .build();
            employeeList.add(employee);
            employeeRepository.save(employee);

            if (comment[i % 3].equals("BEST")) {
                score = 90;
            } else if (comment[i % 3].equals("SOSO")) {
                score = 60;
            } else {
                score = 30;
            }

            evaluationRepository.save(Evaluation.builder()
                    .evalResultScore(score)
                    .evalComment(comment[i % 3])
                    .employee(employeeList.get(i))
                    .work(workList.get(i % 5 == 0 ? ++j : j))
                    .build());
        }
    }

    @Test
    public void 업무추가() throws Exception{
        //given
        Work work = Work.builder()
                .workName("new업무")
                .workChargeName("new담당자")
                .workStartDate(LocalDate.of(2021,1,3))
                .workEndDate(LocalDate.of(2022,6,16))
                .department(departmentRepository.findOne(1L))
                .build();

        //when, then
        assertEquals(work,workService.save(work),"새로운 업무가 추가되었다.");
    }

    @Test
    public void 업무전체검색() throws Exception {
        //given

        //when,then
        for(Work w : workList){
            if(w.getDepartment().getDeptId()==null) {
                Assertions.fail("업무에 부서가 배정되지 않았습니다.");
            }
        }
        for(Employee e : employeeList){
            if(e.getWork().getWorkId()==null){
                Assertions.fail("업무에 직원이 배당되지 않았습니다.");
            }
        }
        assertEquals(workSize,workList.size(),"모든 업무가 검색되었습니다.");
    }


    @Test
    public void 업무상세검색() throws Exception{
        //given
        SearchDTO workSearch = new SearchDTO();

        workSearch.setNameType("deptName");
        workSearch.setName("부서1");

        List<WorkFindDto> findWorks = workService.filteringList(workSearch);

        //when
        assertThat(findWorks.get(0).getDeptName()).isEqualTo("부서1");
    }

    @Test
    public void 업무수정() throws Exception{
        //given
        Work work = workService.findOne(workList.get(0).getWorkId());

        //when
        Work updateWork = workService.update(work.getWorkId(),"update업무",departmentList.get(0).getDeptId(),
                "update담당자",
                LocalDate.of(2020,1,9),
                LocalDate.of(2020,12,21));

        //then
        assertThat(work.getWorkChargeName()).isEqualTo(updateWork.getWorkChargeName());

    }
}
