package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Evaluation;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.EvaluationDTO;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.EvaluationRepository;
import team.okky.personnel_management.repository.WorkRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class EvaluationServiceTest {
    @Autowired private EvaluationService evaluationService;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private WorkRepository workRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EvaluationRepository evaluationRepository;
    int size=10;


    @BeforeEach
    public void init() throws Exception{
        List<Department> departmentList = new ArrayList<>();
        List<Work> workList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        String[] comment = new String[]{"BEST","SOSO","BAD"};

        for (int i = 1; i < 6; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            departmentList.add(department);
        }

        for (int i = 0; i < size; i++) {
            Work work =
                    Work.builder()
                            .workName("업무" + i)
                            .workChargeName("담당자" + i)
                            .workStartDate(LocalDate.of(2020, i + 1, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 4 + i))
                            .department(departmentList.get(i / 2))
                            .build();
            workRepository.save(work);
            workList.add(work);
        }
        int j=-1;
        for (int i = 0; i < 50; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .department(
                            departmentList.get(i / 10)
                    )
                    .work(workList.get(i / 5))
                    .build();
            employeeRepository.save(employee);
            employeeList.add(employee);

            evaluationRepository.save(Evaluation.builder()
                    .evalResultScore((int) (Math.random() * 100) + 1)
                    .evalComment(comment[i % 3])
                    .employee(employeeList.get(i))
                    .work(workList.get(i%5==0?++j:j))
                    .build());
        }
    }

    @Test
    public void 성과목록조회() throws Exception {
        //given
        SearchDTO evalSearch = new SearchDTO();
        evalSearch.setNameType("workName");
        evalSearch.setName("");

        //when
        List<EvaluationDTO.evalBlock> evalBlocks = evaluationService.findAll(evalSearch);

        //then
        Assertions.assertEquals(size,evalBlocks.size());

    }

    @Test
    public void 성과목록_상세조회(){
        //given
        SearchDTO workSearch1 = new SearchDTO();
        SearchDTO workSearch2 = new SearchDTO();
        SearchDTO workSearch3 = new SearchDTO();
        SearchDTO workSearch4 = new SearchDTO();

        workSearch1.setNameType("workName");
        workSearch1.setName("업무0");

        workSearch2.setNameType("empName");
        workSearch2.setName("테스터0");

        workSearch3.setNameType("deptName");
        workSearch3.setName("부서1");

        workSearch4.setNameType("workName");
        workSearch4.setNameType("");

        //when
        List<EvaluationDTO.evalBlock> findEvalBlock1 = evaluationService.findAll(workSearch1);
        List<EvaluationDTO.evalBlock> findEvalBlock2 = evaluationService.findAll(workSearch2);
        List<EvaluationDTO.evalBlock> findEvalBlock3 = evaluationService.findAll(workSearch3);
        List<EvaluationDTO.evalBlock> findEvalBlock4 = evaluationService.findAll(workSearch4);

        //then
        if(!findEvalBlock1.get(0).getEvalInfo().getWorkName().equals("업무0")){
            Assertions.fail("해당 업무명으로 검색되지 않았습니다.");
        }
        for(EvaluationDTO.evalPerWork e: findEvalBlock2.get(0).getEvalPerWorkList()){
            if(!e.getEmpName().contains("테스터0")){
                Assertions.fail("해당 직원명으로 검색되지 않았습니다.");
            }
        }
        if(!findEvalBlock3.get(0).getEvalInfo().getDeptName().equals("부서1")){
            Assertions.fail("해당 부서명으로 검색되지 않았습니다.");
        }

        assertEquals(size,findEvalBlock4.size());
    }

    @Test
    public void 성과수정(){
        //given
        EvaluationDTO.evalBlock selectEvalBlock = evaluationService.findOneByEvalBlock(6L);
        List<EvaluationDTO.evalPerWork> evalPerWorks = selectEvalBlock.getEvalPerWorkList();

        int score = 100;
        String comment = "BEST";

        //when
        Evaluation updateEval = evaluationService.update(evalPerWorks.get(0).getEvalId(),score,comment);

        //then
        assertEquals(score,updateEval.getEvalResultScore());
        assertEquals(comment,updateEval.getEvalComment());
    }
}
