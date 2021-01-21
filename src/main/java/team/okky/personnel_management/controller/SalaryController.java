package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.DepartmentDTO;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.dto.SalaryDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.service.SalaryService;

import java.util.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/salary")
    public List<SalaryDTO> list(@RequestParam("name") String empName){
        return salaryService.filteringList(empName);
    }

    @GetMapping("/salary/edit")
    public SalaryDTO.SalaryForm updateForm(){
        String[] position = new String[]{"대리", "사원", "부장", "본부장", "사장", "차장", "과장"};
        List<String> positionList = Arrays.asList(position);
        List<DepartmentDTO.Name> departmentList = new ArrayList<>();
        for(Department d : departmentRepository.findAll()){
            DepartmentDTO.Name department = DepartmentDTO.Name.builder()
                    .deptId(d.getDeptId())
                    .deptName(d.getDeptName())
                    .build();
            departmentList.add(department);
        }

        List<EmployeeDTO.employeeDTO> employeeList = new ArrayList<>();
        for(Employee e : employeeRepository.findAll()){
            EmployeeDTO.employeeDTO employee = EmployeeDTO.employeeDTO.builder()
                    .empId(e.getEmpId())
                    .empName(e.getEmpName())
                    .build();
            employeeList.add(employee);
        }

        SalaryDTO.SalaryForm salaryForm = SalaryDTO.SalaryForm.builder()
                .department(departmentList)
                .position(positionList)
                .employee(employeeList)
                .build();

        return salaryForm;
    }

    @PostMapping("/salary/edit")
    public String update(@RequestBody SalaryDTO form){
        SalaryDTO salaryDTO = SalaryDTO.builder()
                .deptName(form.getDeptName())
                .empPosition(form.getEmpPosition())
                .empName(form.getEmpName())
                .salary(form.getSalary())
                .incentive(form.getIncentive())
                .build();
        salaryService.update(salaryDTO);
        return "redirect:/salary";
    }
}
