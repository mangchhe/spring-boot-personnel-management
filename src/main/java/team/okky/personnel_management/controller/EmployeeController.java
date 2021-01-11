package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.service.EmployeeService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @GetMapping("/employee")
    public List<EmployeeDTO.ListIndex> viewIndex(){
        return employeeService.viewAll();
    }

    @GetMapping("/employee/search")
    public List<EmployeeDTO.ListIndex> viewByName(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String deptName,
                           @RequestParam(required = false) String empInternalNum){
        List<EmployeeDTO.ListIndex> list = null;
        if(name != null){
            list = employeeService.viewAllById(name);
        }
        else if(deptName != null){
            list = employeeService.viewAllByDept(deptName);
        }
        else if(empInternalNum != null){
            list = employeeService.viewAllByInternalNum(empInternalNum);
        }
        return list;
    }

    @PostMapping("/employee")
    public void viewAddEmployee(@RequestBody EmployeeDTO.AddEmployee addEmployee){
        employeeService.createEmployee(addEmployee);
    }

    @PutMapping("/employee")
    public void viewUpdateEmployee(@RequestBody EmployeeDTO.UpdateEmployee updateEmployee){
        employeeService.updateEmployee(updateEmployee);
    }

    @GetMapping("/employee/duplication/{name}")
    public List<EmployeeDTO.DuplicationName> viewDuplicationName(@PathVariable String name){

        List<EmployeeDTO.DuplicationName> employeeList = employeeService.viewAllByName(name);

        return employeeList;
    }



}
