package team.okky.personnel_management.service;

import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.dto.PageResultDTO;

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
