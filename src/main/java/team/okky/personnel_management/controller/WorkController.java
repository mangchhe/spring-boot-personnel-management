package team.okky.personnel_management.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.EvaluationRepository;
import team.okky.personnel_management.service.WorkService;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/work/create")
    public WorkForm.workAndDept createWorkForm(){
        WorkForm.workAndDept workAndDept = new WorkForm.workAndDept();
        workAndDept.setWork(Work.builder().build());
        workAndDept.setDepartmentList(departmentRepository.findAll());
        return workAndDept;
    }

    @PostMapping("/work/create")
    public String create(@RequestBody WorkForm.workCreateForm workForm){
        Department department = departmentRepository.findOne(workForm.getWorkDept());
        List<Employee> empList = employeeRepository.findAllByDeptId(department.getDeptId());

        Work work = Work.builder()
                .workName(workForm.getWorkName())
                .department(department)
                .workChargeName(workForm.getWorkChargeName())
                .workStartDate(workForm.getWorkStartDate())
                .workEndDate(workForm.getWorkEndDate())
                .build();
        workService.save(work);
        workService.updateWork(empList,work);

        return "redirect:/work";
    }

    @GetMapping("/work")
    public List<WorkFindDto> list(@RequestParam("nameType") String nameType,
                                  @RequestParam("name") String name){
        SearchDTO workSearchDTO = new SearchDTO();
        workSearchDTO.setNameType(nameType);
        workSearchDTO.setName(name);

        return workService.filteringList(workSearchDTO);
    }

    @GetMapping("/work/{workId}/edit")
    public WorkForm.workUpdateForm updateWorkForm(@PathVariable("workId") Long workId){

        WorkForm.workUpdateForm form = new WorkForm.workUpdateForm();
        Work work = workService.findOne(workId);
        form.setWorkName(work.getWorkName());
        form.setDefaultDeptName(work.getDepartment().getDeptName());
        form.setWorkChargeName(work.getWorkChargeName());
        form.setWorkStartDate(work.getWorkStartDate());
        form.setWorkEndDate(work.getWorkEndDate());
        form.setDepartmentList(departmentRepository.findAll());

        return form;
    }

    @PostMapping("/work/{workId}/edit")
    public String update(@PathVariable Long workId,@RequestBody WorkForm.workCreateForm form ){
        workService.update(workId,form.getWorkName(),form.getWorkDept(),form.getWorkChargeName(),
                form.getWorkStartDate(),form.getWorkEndDate());
        return "redirect:/work";
    }
}
