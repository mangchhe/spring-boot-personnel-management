package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.dto.WorkSearchDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.service.WorkService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/work/create")
    public String createWorkForm(Model model){
        model.addAttribute("dept",departmentRepository.findAll());
        model.addAttribute("form",new WorkForm());
        return "/work/createWorkForm";
    }

    @PostMapping("/work/create")
    public String create(@RequestParam("deptId") Long deptId, WorkForm workForm){
        Work work = new Work();
        work.setWork_name(workForm.getWork_name());
        work.setWork_charge_name(workForm.getWork_charge_name());
        work.setWork_start_date(workForm.getWork_start_date());
        work.setWork_end_date(workForm.getWork_end_date());
        work.setDepartment(departmentRepository.findOne(deptId));

        workService.save(work);
        return "redirect:/work";
    }

    @GetMapping("/work")
    public String list(Model model,@RequestParam("nameType") String nameType,
                       @RequestParam("name") String name){
        WorkSearchDTO workSearchDTO = new WorkSearchDTO();
        workSearchDTO.setNameType(nameType);
        workSearchDTO.setName(name);

        List<WorkFindDto> workList = workService.filteringList(workSearchDTO);
        model.addAttribute("workList",workList);
        return "/work";
    }

    @GetMapping("/work/{workId}/edit")
    public String updateWorkForm(@PathVariable("workId") Long workId, Model model){
        Work work = workService.findOne(workId);
        List<Department> department = departmentRepository.findAll();

        WorkForm form = new WorkForm();
        form.setWork_name(work.getWork_name());
        form.setWork_charge_name(work.getWork_charge_name());
        form.setWork_start_date(work.getWork_start_date());
        form.setWork_end_date(work.getWork_end_date());
        form.setDeptName(work.getDepartment().getDept_name());

        model.addAttribute("form",form);
        model.addAttribute("dept",department);

        return "/work/updateWorkForm";
    }

    @PostMapping("/work/{workId}/edit")
    public String update(@PathVariable Long workId, @RequestParam("deptId") Long deptId,
                         @ModelAttribute("form") WorkForm form){
        workService.update(workId,form.getWork_name(),deptId,form.getWork_charge_name(),
                form.getWork_start_date(),form.getWork_end_date());
        return "redirect:/work";
    }
}
