package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.okky.personnel_management.dto.DepartmentDTO;
import team.okky.personnel_management.service.DepartmentService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/department/name")
    public List<DepartmentDTO.Name> viewName(){
        return departmentService.viewIdAndName();
    }
}
