package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import team.okky.personnel_management.domain.AttendanceStatus;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.service.AttendanceService;
import team.okky.personnel_management.service.EmployeeService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class EmployeeController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    @GetMapping("/employee/status/{status}")
    public List<EmployeeDTO.ListByStatus> viewStatusDetail(@PathVariable String status){

        List<EmployeeDTO.ListByStatus> listByStatuses = attendanceService.viewStatusDetail(AttendanceStatus.valueOf(status.toUpperCase()));

        return listByStatuses;
    }

    @GetMapping("/employee/duplication/{name}")
    public List<EmployeeDTO.DuplicationName> viewDuplicationName(@PathVariable String name){

        List<EmployeeDTO.DuplicationName> employeeList = employeeService.viewByName(name);

        return employeeList;
    }
}
