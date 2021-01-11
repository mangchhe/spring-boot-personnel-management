package team.okky.personnel_management.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.WorkSearchDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.WorkRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class WorkServiceTest {
    @Autowired private WorkService workService;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private WorkRepository workRepository;
    @Autowired private EmployeeRepository employeeRepository;

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
        List<Department> departmentList = new ArrayList<>();
        List<Work> workList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        int workSize = 10;

        for (int i = 1; i < 6; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            departmentList.add(department);
        }

        for (int i = 0; i < workSize; i++) {
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
        }

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
        List<Department> departmentList = new ArrayList<>();
        List<Work> workList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        int workSize = 10;

        for (int i = 1; i < 6; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            departmentList.add(department);
        }

        int count=0;
        for (int i = 0; i < workSize; i++) {

            for (int j = 0; j < 5; j++) {
                Employee employee = Employee.builder()
                        .empName("테스터" + count++)
                        .department(
                                departmentList.get(i / 10)
                        )
                        .build();
                employeeRepository.save(employee);
                employeeList.add(employee);
            }

            Work work =
                    Work.builder()
                            .workName("업무" + i)
                            .workChargeName("담당자" + i)
                            .workStartDate(LocalDate.of(2020, i + 1, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 4 + i))
                            .department(departmentList.get(i / 2))
                            .employees(employeeList)
                            .build();
            workRepository.save(work);
            workList.add(work);
        }

        WorkSearchDTO workSearch1 = new WorkSearchDTO();
        WorkSearchDTO workSearch2 = new WorkSearchDTO();
        WorkSearchDTO workSearch3 = new WorkSearchDTO();
        WorkSearchDTO workSearch4 = new WorkSearchDTO();

        workSearch1.setNameType("workName");
        workSearch1.setName("업무0");

        workSearch2.setNameType("empName");
        workSearch2.setName("테스터0");

        workSearch3.setNameType("deptName");
        workSearch3.setName("부서1");

        workSearch4.setNameType("workName");
        workSearch4.setNameType("");

        //when
        List<WorkFindDto> findWorks1 = workService.filteringList(workSearch1);
        List<WorkFindDto> findWorks2 = workService.filteringList(workSearch2);
        List<WorkFindDto> findWorks3 = workService.filteringList(workSearch3);
        List<WorkFindDto> findWorks4 = workService.filteringList(workSearch4);

        //then
        if(!findWorks1.get(0).getWorkName().equals("업무0")){
            Assertions.fail("해당 업무명으로 검색되지 않았습니다.");
        }
        for(WorkFindDto w: findWorks2){
            if(w.getWorkName()==null){
                Assertions.fail("해당 직원명으로 검색되지 않았습니다.");
            }
        }
        if(!findWorks3.get(0).getDeptName().equals("부서1")){
            Assertions.fail("해당 부서명으로 검색되지 않았습니다.");
        }
        assertEquals(workSize,findWorks4.size());
    }

    @Test
    public void 업무수정() throws Exception{
        //given
        List<Department> departmentList = new ArrayList<>();
        List<Work> workList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            departmentList.add(department);
        }

        for(int i=0;i<3;i++) {
            Work work =
                    Work.builder()
                            .workName("업무" + i)
                            .workChargeName("담당자" + i)
                            .workStartDate(LocalDate.of(2020, i + 1, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 4 + i))
                            .department(departmentList.get(i / 2))
                            .employees(employeeList)
                            .build();
            workRepository.save(work);
            workList.add(work);
        }

        Work work = workService.findOne(workList.get(0).getWorkId());
        String chargeName = work.getWorkChargeName();

        //when
        Work updateWork = workService.update(work.getWorkId(),"update업무",departmentList.get(0).getDeptId(),
                "update담당자",
                LocalDate.of(2020,1,9),
                LocalDate.of(2020,12,21));

        //then
        if(chargeName.equals(updateWork.getWorkChargeName())){
            Assertions.fail("업무 상세정보가 수정되지 않았습니다.");
        }
        else {
            Assertions.assertTrue(true,"업무 상세정보가 수정되었습니다.");
        }

    }
}
