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
    public EmployeeDTO.ListIndex viewIndex(){
        return modelMapper.map(new Employee(), EmployeeDTO.ListIndex.class);
    }

    @GetMapping("/employee/search")
    public EmployeeDTO.ListIndex viewByName(@RequestParam(required = false) Long name,
                           @RequestParam(required = false) String deptName,
                           @RequestParam(required = false) String empInternalNum){
        return modelMapper.map(new Employee(), EmployeeDTO.ListIndex.class);
    }

    @PostMapping("/employee")
    public void viewAddEmployee(@RequestBody EmployeeDTO.AddEmployee addEmployee){

    }

    @PutMapping("/employee")
    public void viewUpdateEmployee(@RequestBody EmployeeDTO.UpdateEmployee updateEmployee){

    }

    @GetMapping("/employee/duplication/{name}")
    public List<EmployeeDTO.DuplicationName> viewDuplicationName(@PathVariable String name){

        List<EmployeeDTO.DuplicationName> employeeList = employeeService.viewByName(name);

        return employeeList;
    }



}
