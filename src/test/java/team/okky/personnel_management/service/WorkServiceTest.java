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

/*    @Test
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
    public void 업무전체검색() throws Exception{
        //given
        List<WorkFindDto> findWorks = workService.findAll();
        //when,then
        assertEquals(11,findWorks.size(),"새로 추가된 업무까지 전체가 검색되었습니다");
    }

    @Test
    public void 업무상세검색() throws Exception{
        //given
        WorkSearchDTO workSearch = new WorkSearchDTO();
        workSearch.setNameType("workName");
        workSearch.setName("업무0");

        //when,then
        List<WorkFindDto> findWorks = workService.filteringList(workSearch);
        assertEquals("업무0",findWorks.get(0).getWorkName());
    }

    @Test
    public void 업무수정() throws Exception{
        //given
        Work work = workService.findOne(1L);
        String chargeName = work.getWork_charge_name();

        //when
        Work updateWork = workService.update(work.getWork_id(),"인공지능",1L,"honggildong",
                LocalDate.of(2020,1,9),
                LocalDate.of(2020,12,21));

        //then
        if(chargeName.equals(updateWork.getWork_charge_name())){
            Assertions.fail("업무 상세정보가 수정되지 않았습니다.");
        }
        else {
            Assertions.assertTrue(true,"업무 상세정보가 수정되었습니다.");
        }

    }*/
}
