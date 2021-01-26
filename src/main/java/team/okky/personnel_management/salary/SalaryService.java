package team.okky.personnel_management.salary;

import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;

public interface SalaryService {
    public List<SalaryDTO.indexSalary> viewAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllForPage(int pageNo);
    public List<SalaryDTO.indexSalary> viewAllByName(String empName,PageRequestDTO PageRequestDTO);
    public PageResultDTO viewAllByNameForPage(String empName,int pageNo);
    public List<SalaryDTO.indexSalary> findByName(String empName);
    public SalaryDTO.indexSalary salaryListPerEmployee(Employee e);
    public void update(SalaryDTO.updateForm salaryPerEmp);
}
