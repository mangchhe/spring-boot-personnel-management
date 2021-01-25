package team.okky.personnel_management.service;

import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.dto.PageResultDTO;
import team.okky.personnel_management.dto.SalaryDTO;

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
