package team.okky.personnel_management.work;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.department.DepartmentDTO;
import team.okky.personnel_management.department.DepartmentService;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.employee.EmployeeRepository;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @GetMapping("/work/create")
    public List<DepartmentDTO.Name> createWorkForm(){
        return departmentService.viewIdAndName();
    }

    @PostMapping("/work/create")
    public String create(@RequestBody WorkDTO.WorkCreateForm workForm){
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
        workService.allocationNewWork(empList,work);

        return "redirect:/work";
    }

    @GetMapping("/work")
    public WorkDTO.IndexPage list(@RequestParam("nameType") String nameType,
                                  @RequestParam("name") String name,@RequestParam(value = "page", defaultValue = "1") Integer pageNo){
        List<WorkDTO.indexWork> list = null;
        PageResultDTO pageResultDTO = null;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        if(nameType.equals("workName") && !name.isEmpty()){
            list = workService.viewAllByWorkName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByWorkNameForPage(name,pageNo);
        }
        else if(nameType.equals("deptName") && !name.isEmpty()){
            list = workService.viewAllByDeptName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByDeptNameForPage(name,pageNo);
        }
        else if(nameType.equals("empName")&& !name.isEmpty()){
            list = workService.viewAllByEmpName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByEmpNameForPage(name,pageNo);
        }
        else{
            list = workService.viewAll(pageRequestDTO);
            pageResultDTO = workService.viewAllForPage(pageNo);
        }
        return WorkDTO.IndexPage.builder()
                .list(list)
                .pageResultDTO(pageResultDTO)
                .build();

    }

    @GetMapping("/work/{workId}/edit")
    public WorkDTO.WorkUpdateForm updateWorkForm(@PathVariable("workId") Long workId){
        Work work = workService.findOne(workId);
        return WorkDTO.WorkUpdateForm.builder()
                .workName(work.getWorkName())
                .defaultDeptName(work.getDepartment().getDeptName())
                .workChargeName(work.getWorkChargeName())
                .workStartDate(work.getWorkStartDate())
                .workEndDate(work.getWorkEndDate())
                .departmentList(departmentService.viewIdAndName())
                .build();
    }

    @PutMapping("/work/{workId}/edit")
    public String update(@PathVariable Long workId,@RequestBody WorkDTO.WorkCreateForm form ){
        workService.update(workId,form.getWorkName(),form.getWorkDept(),form.getWorkChargeName(),
                form.getWorkStartDate(),form.getWorkEndDate());
        return "redirect:/work";
    }
}
