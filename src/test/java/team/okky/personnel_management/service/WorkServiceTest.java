package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.WorkSearchDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.dto.WorkFindDto;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Work work = new Work();
        work.setWorkName("VR");
        work.setWorkChargeName("jungha");
        work.setWorkStartDate(LocalDate.of(2021,1,3));
        work.setWorkEndDate(LocalDate.of(2022,6,16));
        work.setDepartment(departmentRepository.findOne(2L));

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
            System.out.println("업무명=" + dto.getWorkName());
            System.out.println("업무명과 연관된 부서명 =" + dto.getDeptName());
            System.out.println("업무를 맡은 직원들=" + dto.getEmployees());
            System.out.println("업무 시작일 =" + dto.getWorkStartDate());
            System.out.println("업무 진행상태="+dto.getWorkStatus());
            System.out.println("====================================");
        }

        //then
        assertEquals(findWorksSize,countDTO,"업무전체가 검색되었습니다.");
    }

    @Test
    public void 업무상세검색() throws Exception{
        //given
        WorkSearchDTO workSearch = new WorkSearchDTO();
        workSearch.setNameType("empName");
        workSearch.setName("honggildong");

        //when
        List<WorkFindDto> findWorks = workService.filteringList(workSearch);
        for (WorkFindDto dto : findWorks) {
            System.out.println("업무명=" + dto.getWorkName());
            System.out.println("업무명과 연관된 부서명 =" + dto.getDeptName());
            System.out.println("업무를 맡은 직원들=" + dto.getEmployees());
            System.out.println("업무 시작일 =" + dto.getWorkStartDate());
            System.out.println("업무 진행상태="+dto.getWorkStatus());
            System.out.println("====================================");
        }

    }

}