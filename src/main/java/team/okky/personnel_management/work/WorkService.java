package team.okky.personnel_management.work;

import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;

public interface WorkService {
    public Work save(Work work);
    public Work findOne(Long id);
    public List<WorkDTO.indexWork> viewAllByWorkName(String name,PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllByWorkNameForPage(String name, int pageNo);
    public List<WorkDTO.indexWork> viewAllByDeptName(String name,PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllByDeptNameForPage(String name, int pageNo);
    public List<WorkDTO.indexWork> viewAllByEmpName(String name,PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllByEmpNameForPage(String name, int pageNo);
    public List<WorkDTO.indexWork> viewAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllForPage(int pageNo);

    public WorkDTO.indexWork fillWorkBlock(Work work);
    public Boolean findStatus(LocalDate workStartDate, LocalDate workEndDate);
    public String empNamePerWork(List<Employee> list);

    public Work update(Long workId, String workName,Long deptId, String chargeName, LocalDate startDate, LocalDate endDate);
    public void allocationNewWork(List<Employee> empList,Work newWork);
}
