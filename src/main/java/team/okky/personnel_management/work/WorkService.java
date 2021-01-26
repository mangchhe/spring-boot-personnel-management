package team.okky.personnel_management.work;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.evaluation.Evaluation;
import team.okky.personnel_management.utils.dto.SearchDTO;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.evaluation.EvaluationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkService {
    private final WorkRepository workRepository;
    private final DepartmentRepository departmentRepository;
    private final EvaluationRepository evaluationRepository;

    @Transactional
    public Work save(Work work){ return workRepository.save(work); }

    public Work findOne(Long id){
        return workRepository.findOne(id);
    }

    public List<WorkFindDto> filteringList(SearchDTO workSearch) {
        String nameType = workSearch.getNameType();
        String name = workSearch.getName();
        List<WorkFindDto> workBlockList = new ArrayList<>();

        if(nameType.equals("workName") && !name.isEmpty()) {
            Work work = workRepository.findByWorkName(name);
            workBlockList = fillWorkBlockList(work);
        }
        else if(nameType.equals("deptName")&& !name.isEmpty()) {
            List<Work> work = workRepository.findByDeptName(name);

            for(Work w : work) {
                List<Employee> empList = evaluationRepository.empListPerWork(w.getWorkId());
                WorkFindDto workFindDto = WorkFindDto.builder()
                        .workId(w.getWorkId())
                        .workName(w.getWorkName())
                        .workChargeName(w.getWorkChargeName())
                        .workStartDate(w.getWorkStartDate())
                        .workEndDate(w.getWorkEndDate())
                        .deptName(w.getDepartment().getDeptName())
                        .employees(empNamePerWork(empList))
                        .workStatus(findStatus(w.getWorkStartDate(), w.getWorkEndDate()))
                        .build();
                workBlockList.add(workFindDto);
            }
        }
        else if(nameType.equals("empName")&& !name.isEmpty()){
            Work work = workRepository.findByEmpName(name);
            workBlockList = fillWorkBlockList(work);
        }
        else {
            List<Work> work = workRepository.findAll();

            for(Work w : work) {
                List<Employee> empList = evaluationRepository.empListPerWork(w.getWorkId());
                WorkFindDto workFindDto = WorkFindDto.builder()
                        .workId(w.getWorkId())
                        .workName(w.getWorkName())
                        .workChargeName(w.getWorkChargeName())
                        .workStartDate(w.getWorkStartDate())
                        .workEndDate(w.getWorkEndDate())
                        .deptName(w.getDepartment().getDeptName())
                        .employees(empNamePerWork(empList))
                        .workStatus(findStatus(w.getWorkStartDate(), w.getWorkEndDate()))
                        .build();
                workBlockList.add(workFindDto);
            }
        }
        return workBlockList;
    }

    public List<WorkFindDto> fillWorkBlockList(Work work){
        List<WorkFindDto> workBlockList = new ArrayList<>();
        List<Employee> empList = evaluationRepository.empListPerWork(work.getWorkId());

        WorkFindDto workFindDto = WorkFindDto.builder()
                .workId(work.getWorkId())
                .workName(work.getWorkName())
                .workChargeName(work.getWorkChargeName())
                .workStartDate(work.getWorkStartDate())
                .workEndDate(work.getWorkEndDate())
                .deptName(work.getDepartment().getDeptName())
                .employees(empNamePerWork(empList))
                .workStatus(findStatus(work.getWorkStartDate(), work.getWorkEndDate()))
                .build();
        workBlockList.add(workFindDto);
        return workBlockList;
    }

    public Boolean findStatus(LocalDate workStartDate, LocalDate workEndDate) {
        LocalDate today = LocalDate.now();
        if(today.isAfter(workStartDate)&& (today.isBefore(workEndDate)) || today.equals(workStartDate) || today.equals(workEndDate)){
            return true;
        }
        else {
            return false;
        }
    }

    public String empNamePerWork(List<Employee> list){
        String empName="";
        for(Employee e : list ) {
            empName = empName+" "+e.getEmpName();
        }
        return empName;
    }



    @Transactional
    public Work update(Long workId, String workName, Long deptId, String chargeName, LocalDate startDate, LocalDate endDate){
        Work findWork = workRepository.findOne(workId);
        Department selectDept = departmentRepository.findOne(deptId);

        findWork.change(workName, chargeName, startDate, endDate, selectDept);
        return findWork;
    }

    @Transactional
    public void updateWork(List<Employee> empList,Work newWork){
        String[] comment = new String[]{"BEST","SOSO","BAD"};
        int i=0;
        int score = 0;
        for(Employee e: empList){
            e.setWork(newWork);
            if(comment[i%3].equals("BEST")){
                score = 90;
            }
            else if(comment[i%3].equals("SOSO")){
                score = 60;
            }
            else{ score = 30;}
            Evaluation newEval = Evaluation.builder()
                    .evalResultScore(score)
                    .evalComment(comment[i%3])
                    .work(newWork)
                    .employee(e)
                    .build();
            i++;
            evaluationRepository.save(newEval);
        }
    }
}
