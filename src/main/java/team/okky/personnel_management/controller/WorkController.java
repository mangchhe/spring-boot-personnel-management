package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonBuilderUtils;
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
@Slf4j
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
    public String create(@RequestBody WorkForm.workCreateForm workForm){
        Work work = Work.builder()
                .workName(workForm.getWorkName())
                .department(departmentRepository.findOne(workForm.getWorkDept()))
                .workChargeName(workForm.getWorkChargeName())
                .workStartDate(workForm.getWorkStartDate())
                .workEndDate(workForm.getWorkEndDate())
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
    public String update(@PathVariable Long workId,@RequestBody WorkForm.workCreateForm form ){
        workService.update(workId,form.getWorkName(),form.getWorkDept(),form.getWorkChargeName(),
                form.getWorkStartDate(),form.getWorkEndDate());
        return "redirect:/work";
    }
}
