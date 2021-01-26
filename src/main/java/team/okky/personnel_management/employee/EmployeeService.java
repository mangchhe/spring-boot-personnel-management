package team.okky.personnel_management.employee;

import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;


public interface EmployeeService {

    public List<EmployeeDTO.ListIndex> viewAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllForPage(int pageNo);
    public List<EmployeeDTO.ListIndex> viewAllByName(String name, PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllByNameForPage(String name, int pageNo);
    public List<EmployeeDTO.ListIndex> viewAllByDept(String deptName, PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllByDeptNameForPage(String deptName, int pageNo);
    public Employee createEmployee(EmployeeDTO.AddEmployee addEmployee);
    public Employee updateEmployee(EmployeeDTO.UpdateEmployee updateEmployee);



}
