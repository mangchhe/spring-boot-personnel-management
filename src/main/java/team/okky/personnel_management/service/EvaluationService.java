package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Evaluation;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.dto.EvaluationDTO;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.EvaluationRepository;
import team.okky.personnel_management.repository.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final WorkRepository workRepository;

    @Transactional
    public Evaluation save(Evaluation evaluation){ return evaluationRepository.save(evaluation);}

    public Evaluation findOne(Long id){ return evaluationRepository.findOne(id);}

    public List<EvaluationDTO.evalBlock> findAll() {
        List<EvaluationDTO.evalBlock> list = new ArrayList<>();
        List<Long> workIdList = workRepository.findWorkId();
        List<EvaluationDTO.evalPerWork> evalPerWorkList = new ArrayList<>();

        for(Long workId : workIdList) {

            for (Evaluation e : evaluationRepository.findByWork(workId)) {
                EvaluationDTO.evalPerWork evalPerWork = EvaluationDTO.evalPerWork.builder()
                        .empId(e.getEmployee().getEmpId())
                        .empName(e.getEmployee().getEmpName())
                        .score(e.getEvalResultScore())
                        .comment(e.getEvalComment())
                        .build();
                evalPerWorkList.add(evalPerWork);
            }
                Work work = workRepository.findOne(workId);
                EvaluationDTO.EvalInfo evalInfo = EvaluationDTO.EvalInfo.builder()
                        .evalBlockId(workId)
                        .deptName(work.getDepartment().getDeptName())
                        .workName(work.getWorkName())
                        .build();

            EvaluationDTO.evalBlock evalBlock = new EvaluationDTO.evalBlock();
            evalBlock.setEvalInfo(evalInfo);
            evalBlock.setEvalPerWorkList(evalPerWorkList);
            list.add(evalBlock);
        }
        return list;
    }
}
