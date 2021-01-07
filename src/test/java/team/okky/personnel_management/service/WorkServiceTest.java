package team.okky.personnel_management.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.controller.WorkSearch;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.dto.WorkFindDto;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class WorkServiceTest {
    @Autowired WorkService workService;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired EmployeeRepository employeeRepository;

    @Test
    public void 업무추가() throws Exception{
        //given
        Department department = new Department("R&D","jaeseok","5F");
        departmentRepository.save(department);

        Work work = new Work();
        work.setWork_name("VR");
        work.setWork_charge_name("jaeseok");
        work.setWork_start_date(LocalDate.of(2021,12,1));
        work.setWork_end_date(LocalDate.of(2022,5,10));
        work.setDepartment(department);

        //when, then
        assertEquals(work,workService.save(work),"업무 추가되었다.");
    }

    @Test
    public void 업무전체검색() throws Exception{
        //given
        List<WorkFindDto> findWorks = workService.findAll();
        int findWorksSize = findWorks.size();
        int countDTO=0;

        //when
        for (WorkFindDto dto : findWorks) {
            countDTO++;
            System.out.println("업무명=" + dto.getWork_name());
            System.out.println("업무명과 연관된 부서명 =" + dto.getDept_name());
            System.out.println("업무를 맡은 직원들=" + dto.getEmployees());
            System.out.println("업무 시작일 =" + dto.getWork_start_date());
            System.out.println("업무 진행상태="+dto.getWorkStatus());
            System.out.println("====================================");
        }

        //then
        assertEquals(findWorksSize,countDTO,"업무전체가 검색되었습니다.");
    }

    @Test
    public void 업무상세검색() throws Exception{
        //given
        WorkSearch workSearch = new WorkSearch();
        workSearch.setNameType("empName");
        workSearch.setName("honggildong");

        //when
        List<WorkFindDto> findWorks = workService.filteringList(workSearch);
        for (WorkFindDto dto : findWorks) {
            System.out.println("업무명=" + dto.getWork_name());
            System.out.println("업무명과 연관된 부서명 =" + dto.getDept_name());
            System.out.println("업무를 맡은 직원들=" + dto.getEmployees());
            System.out.println("업무 시작일 =" + dto.getWork_start_date());
            System.out.println("업무 진행상태="+dto.getWorkStatus());
            System.out.println("====================================");
        }

        List<String> empList = findWorks.get(0).getEmployees();

        //then
        assertTrue(empList.contains(workSearch.getName()));
    }

    @Test
    public void 업무수정() throws Exception{
        //given
        Work work = workService.findOne(1L);
        String chargeName = work.getWork_charge_name();

        //when
        Work updateWork = workService.update(work.getWork_id(),"클라우드",1L,"miho",
                LocalDate.of(2020,1,4),
                LocalDate.of(2020,9,16));

        //then
        if(chargeName.equals(updateWork.getWork_charge_name())){
            Assertions.fail("업무 상세정보가 수정되지 않았습니다.");
        }
        else {
            Assertions.assertTrue(true,"업무 상세정보가 수정되었습니다.");
        }

    }
}