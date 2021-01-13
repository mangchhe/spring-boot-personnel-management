package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.service.WorkService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/work/create")
    public WorkForm.workAndDept createWorkForm(){
        WorkForm.workAndDept workAndDept = new WorkForm.workAndDept();
        workAndDept.setWork(Work.builder().build());
        workAndDept.setDepartmentList(departmentRepository.findAll());
        return workAndDept;
    }

    @PostMapping("/work/create")
    public String create(@RequestParam("deptId") Long deptId,
                         WorkForm.workAndDept workForm){
        Work work = Work.builder()
                .workName(workForm.getWork().getWorkName())
                .workChargeName(workForm.getWork().getWorkChargeName())
                .workStartDate(workForm.getWork().getWorkStartDate())
                .workEndDate(workForm.getWork().getWorkEndDate())
                .department(departmentRepository.findOne(deptId))
                .build();
        workService.save(work);
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
    public WorkForm.workAndDept updateWorkForm(@PathVariable("workId") Long workId){
        Work work = workService.findOne(workId);
        WorkForm.workAndDept form = new WorkForm.workAndDept();
        form.setWork(work);
        form.setDefaultDeptName(work.getDepartment().getDeptName());
        form.setDepartmentList(departmentRepository.findAll());

        return form;
    }

    @PostMapping("/work/{workId}/edit")
    public String update(@PathVariable Long workId, @RequestParam("deptId") Long deptId,
                         @ModelAttribute("form") WorkForm.workAndDept form){
        workService.update(workId,form.getWork().getWorkName(),deptId,form.getWork().getWorkChargeName(),
                form.getWork().getWorkStartDate(),form.getWork().getWorkEndDate());
        return "redirect:/work";
    }
}
