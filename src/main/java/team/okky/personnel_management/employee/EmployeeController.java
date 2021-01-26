package team.okky.personnel_management.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public EmployeeDTO.ListIndexPage viewByName(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String deptName,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer pageNo){
        List<EmployeeDTO.ListIndex> list = null;
        PageResultDTO pageResultDTO = null;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        if(name != null){
            list = employeeService.viewAllByName(name, pageRequestDTO);
            pageResultDTO = employeeService.viewAllByNameForPage(name, pageNo);
        }
        else if(deptName != null){
            list = employeeService.viewAllByDept(deptName, pageRequestDTO);
            pageResultDTO = employeeService.viewAllByDeptNameForPage(deptName, pageNo);
        }
        else{
            list = employeeService.viewAll(pageRequestDTO);
            pageResultDTO = employeeService.viewAllForPage(pageNo);
        }
        return EmployeeDTO.ListIndexPage.builder()
                .list(list)
                .pageResultDTO(pageResultDTO)
                .build();
    }

    @PostMapping("/employee")
    public void viewAddEmployee(@RequestBody EmployeeDTO.AddEmployee addEmployee){
        employeeService.createEmployee(addEmployee);
    }

    @PutMapping("/employee")
    public void viewUpdateEmployee(@RequestBody EmployeeDTO.UpdateEmployee updateEmployee){
        employeeService.updateEmployee(updateEmployee);
    }

}
