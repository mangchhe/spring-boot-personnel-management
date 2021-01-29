package team.okky.personnel_management.employee;

import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;


public interface EmployeeService {

    public Employee createEmployee(EmployeeDTO.AddForm addForm);
    public Employee updateEmployee(EmployeeDTO.UpdateForm updateForm);
    public List<EmployeeDTO.Index> viewAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO viewPage(int pageNo);
    public List<EmployeeDTO.Index> viewAllByName(String name, PageRequestDTO pageRequestDTO);
    public PageResultDTO viewPageByName(String name, int pageNo);
    public List<EmployeeDTO.Index> viewAllByDept(String deptName, PageRequestDTO pageRequestDTO);
    public PageResultDTO viewPageByDeptName(String deptName, int pageNo);

}
