package team.okky.personnel_management.work;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.evaluation.Evaluation;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.evaluation.EvaluationRepository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkServiceImpl implements WorkService{
    private final WorkRepository workRepository;
    private final DepartmentRepository departmentRepository;
    private final EvaluationRepository evaluationRepository;

    @Transactional
    @Override
    public Work save(Work work){ return workRepository.save(work); }

    @Override
    public Work findOne(Long id){
        return workRepository.findOne(id);
    }

    @Override
    public List<WorkDTO.indexWork> viewAllByWorkName(String name,PageRequestDTO pageRequestDTO){
        List<Work> work = workRepository.findByWorkName(name,pageRequestDTO);
        List<WorkDTO.indexWork> workBlockList = new ArrayList<>();

        for(Work w : work) {
            workBlockList.add(fillWorkBlock(w));
        }
        return workBlockList;
    }

    @Override
    public PageResultDTO viewAllByWorkNameForPage(String name, int pageNo){
        return new PageResultDTO(workRepository.findTotalByWorkName(name),pageNo);
    }

    @Override
    public List<WorkDTO.indexWork> viewAllByDeptName(String name,PageRequestDTO pageRequestDTO){
        List<WorkDTO.indexWork> workBlockList = new ArrayList<>();
        List<Work> work = workRepository.findByDeptName(name,pageRequestDTO);

        for(Work w : work) {
            workBlockList.add(fillWorkBlock(w));
        }
        return workBlockList;
    }

    @Override
    public PageResultDTO viewAllByDeptNameForPage(String name, int pageNo){
        return new PageResultDTO(workRepository.findTotalByDeptName(name),pageNo);
    }

    @Override
    public List<WorkDTO.indexWork> viewAllByEmpName(String name,PageRequestDTO pageRequestDTO){
        List<WorkDTO.indexWork> workBlockList = new ArrayList<>();
        List<Work> work = workRepository.findByEmpName(name,pageRequestDTO);

        for(Work w : work) {
            workBlockList.add(fillWorkBlock(w));
        }
        return workBlockList;
    }

    @Override
    public PageResultDTO viewAllByEmpNameForPage(String name, int pageNo){
        return new PageResultDTO(workRepository.findTotalByEmpName(name),pageNo);
    }

    @Override
    public List<WorkDTO.indexWork> viewAll(PageRequestDTO pageRequestDTO){
        List<WorkDTO.indexWork> workBlockList = new ArrayList<>();
        List<Work> work = workRepository.findAll(pageRequestDTO);

        for(Work w : work) {
            workBlockList.add(fillWorkBlock(w));
        }
        return workBlockList;
    }

    @Override
    public PageResultDTO viewAllForPage(int pageNo){
        return new PageResultDTO(workRepository.findTotal(),pageNo);
    }

    /*
     *업무상세정보 생성
     */
    @Override
    public WorkDTO.indexWork fillWorkBlock(Work work){
        List<Employee> empList = evaluationRepository.empListPerWork(work.getWorkId());

        WorkDTO.indexWork workFindDto = WorkDTO.indexWork.builder()
                .workId(work.getWorkId())
                .workName(work.getWorkName())
                .workChargeName(work.getWorkChargeName())
                .workStartDate(work.getWorkStartDate())
                .workEndDate(work.getWorkEndDate())
                .deptName(work.getDepartment().getDeptName())
                .employees(empNamePerWork(empList))
                .workStatus(findStatus(work.getWorkStartDate(), work.getWorkEndDate()))
                .build();
        return workFindDto;
    }

    /*
     *업무 진행상태
     */
    @Override
    public Boolean findStatus(LocalDate workStartDate, LocalDate workEndDate) {
        LocalDate today = LocalDate.now();
        if(today.isAfter(workStartDate)&& (today.isBefore(workEndDate)) || today.equals(workStartDate) || today.equals(workEndDate)){
            return true;
        }
        else {
            return false;
        }
    }

    /*
     *업무 배정된 직원이름
     */
    @Override
    public String empNamePerWork(List<Employee> list){
        String empName="";
        for(Employee e : list ) {
            empName = empName+" "+e.getEmpName();
        }
        return empName;
    }


    /*
     *업무 상세정보 수정
     */
    @Transactional
    @Override
    public Work update(Long workId, String workName, Long deptId, String chargeName, LocalDate startDate, LocalDate endDate){
        Work findWork = workRepository.findOne(workId);
        Department selectDept = departmentRepository.findOne(deptId);

        findWork.change(workName, chargeName, startDate, endDate, selectDept);
        return findWork;
    }

    /*
     *새로운 업무배당시 성과자동생성
     */
    @Transactional
    @Override
    public void allocationNewWork(List<Employee> empList,Work newWork){
        String[] comment = new String[]{"BEST","SOSO","BAD"};
        int i=0;
        int score = 0;
        for(Employee e: empList){
            e.changeWork(newWork);
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
