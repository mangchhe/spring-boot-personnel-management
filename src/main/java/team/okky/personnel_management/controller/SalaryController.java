package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.*;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.service.SalaryService;

import java.util.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class SalaryController {
    private final SalaryService salaryService;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/salary")
    public List<SalaryDTO> list(@RequestParam("name") String empName){
        return salaryService.filteringList(empName);
    }

    @GetMapping("/salary/{empId}/edit")
    public SalaryDTO.SalaryForm updateForm(@PathVariable Long empId){
        Employee selectEmployee = employeeRepository.findOne(empId);
        List<SalaryDTO> salaryPerEmp = salaryService.filteringList(selectEmployee.getEmpName());

        SalaryDTO.SalaryForm salaryForm = SalaryDTO.SalaryForm.builder()
                .deptName(selectEmployee.getDepartment().getDeptName())
                .position(selectEmployee.getEmpPosition())
                .empName(selectEmployee.getEmpName())
                .salary(salaryPerEmp.get(0).getSalary())
                .incentive(salaryPerEmp.get(0).getIncentive())
                .build();

        return salaryForm;
    }

    @PostMapping("/salary/{empId}/edit")
    public String update(@PathVariable Long empId, @RequestBody SalaryDTO.SalaryList form){
        SalaryDTO.SalaryList salaryPerEmp = SalaryDTO.SalaryList.builder()
                .empId(empId)
                .salary(form.getSalary())
                .incentive(form.getIncentive())
                .build();
        salaryService.update(salaryPerEmp);
        return "redirect:/salary";
    }
}
