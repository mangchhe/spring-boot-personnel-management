package team.okky.personnel_management.salary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class SalaryController {
    private final SalaryService salaryService;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/salary")
    public SalaryDTO.indexPage list(@RequestParam(value = "name",required = false) String empName,@RequestParam(value = "page", defaultValue = "1") Integer pageNo){
        List<SalaryDTO.indexSalary> list = null;
        PageResultDTO pageResultDTO = null;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);
        if(empName!=null) {
            list = salaryService.viewAllByName(empName,pageRequestDTO);
            pageResultDTO = salaryService.viewAllByNameForPage(empName, pageNo);
        }
        else{
            list = salaryService.viewAll(pageRequestDTO);
            pageResultDTO = salaryService.viewAllForPage(pageNo);
        }
        return SalaryDTO.indexPage.builder()
                .list(list)
                .pageResultDTO(pageResultDTO)
                .build();
    }

    @GetMapping("/salary/{empId}/edit")
    public SalaryDTO.SalaryForm updateForm(@PathVariable Long empId){
        Employee selectEmployee = employeeRepository.findOne(empId);
        List<SalaryDTO.indexSalary> salaryPerEmp = salaryService.findByName(selectEmployee.getEmpName());

        SalaryDTO.SalaryForm salaryForm = SalaryDTO.SalaryForm.builder()
                .deptName(selectEmployee.getDepartment().getDeptName())
                .position(selectEmployee.getEmpPosition().getPosition())
                .empName(selectEmployee.getEmpName())
                .salary(salaryPerEmp.get(0).getSalary())
                .incentive(salaryPerEmp.get(0).getIncentive())
                .build();

        return salaryForm;
    }

    @PostMapping("/salary/{empId}/edit")
    public String update(@PathVariable Long empId, @RequestBody SalaryDTO.updateForm form){
        SalaryDTO.updateForm salaryPerEmp = SalaryDTO.updateForm.builder()
                .empId(empId)
                .salary(form.getSalary())
                .incentive(form.getIncentive())
                .build();
        salaryService.update(salaryPerEmp);
        return "redirect:/salary";
    }
}
