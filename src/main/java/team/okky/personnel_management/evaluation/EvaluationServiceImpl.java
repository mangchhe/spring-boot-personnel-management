package team.okky.personnel_management.evaluation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.work.Work;
import team.okky.personnel_management.work.WorkDTO;
import team.okky.personnel_management.work.WorkRepository;
import team.okky.personnel_management.work.WorkService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final WorkRepository workRepository;
    private final WorkService workService;

    @Transactional
    @Override
    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation findOne(Long id) {
        return evaluationRepository.findOne(id);
    }

    @Override
    public List<EvaluationDTO.EvalBlock> findByWorkName(String name) {
        List<EvaluationDTO.EvalBlock> list = new ArrayList<>();
        List<EvaluationDTO.EvalPerWork> evalListPerWork = new ArrayList<>();
        List<Evaluation> evalList = evaluationRepository.findByWorkName(name);
        for (Evaluation e : evalList) {
            evalListPerWork.add(addEvalListPerWork(e));
        }

        Work work = workRepository.findOne(evalList.get(0).getWork().getWorkId());
        EvaluationDTO.EvalInfo evalInfo = addEvalInfo(work);

        list.add(EvaluationDTO.EvalBlock.builder()
                .evalInfo(evalInfo)
                .evalListPerWork(evalListPerWork)
                .build());
        return list;
    }

    @Override
    public List<EvaluationDTO.EvalBlock> viewAllByWorkName(String name, PageRequestDTO pageRequestDTO) {
        List<WorkDTO.indexWork> workList = workService.viewAllByWorkName(name, pageRequestDTO);
        return addEvalBlock(workList);
    }

    @Override
    public List<EvaluationDTO.EvalBlock> viewAllByDeptName(String name,PageRequestDTO pageRequestDTO){
        List<WorkDTO.indexWork> workList = workService.viewAllByDeptName(name,pageRequestDTO);
        return addEvalBlock(workList);
    }

    @Override
    public List<EvaluationDTO.EvalBlock> viewAllByEmpName(String name,PageRequestDTO pageRequestDTO) {
        List<WorkDTO.indexWork> workList = workService.viewAllByEmpName(name, pageRequestDTO);
        return addEvalBlock(workList);
    }

    @Override
    public List<EvaluationDTO.EvalBlock> viewAll(PageRequestDTO pageRequestDTO) {
        List<WorkDTO.indexWork> workList = workService.viewAll(pageRequestDTO);
        return addEvalBlock(workList);
    }

    @Override
    public List<EvaluationDTO.EvalBlock> addEvalBlock(List<WorkDTO.indexWork> workList) {
        List<EvaluationDTO.EvalBlock> list = new ArrayList<>();

        for (WorkDTO.indexWork w : workList) {
            EvaluationDTO.EvalInfo evalInfo = addEvalInfo(workService.findOne(w.getWorkId()));

            List<EvaluationDTO.EvalPerWork> evalPerWork = new ArrayList<>();
            for (Evaluation e : evaluationRepository.findAllEvalPerWork(w.getWorkId())) {
                evalPerWork.add(addEvalListPerWork(e));
            }

            list.add(EvaluationDTO.EvalBlock.builder()
                    .evalInfo(evalInfo)
                    .evalListPerWork(evalPerWork)
                    .build());
        }
        return list;
    }

    @Override
    public EvaluationDTO.EvalPerWork addEvalListPerWork(Evaluation e){
        return EvaluationDTO.EvalPerWork.builder()
                .evalId(e.getEvalId())
                .empName(e.getEmployee().getEmpName())
                .score(e.getEvalResultScore())
                .comment(e.getEvalComment())
                .build();
    }

    @Override
    public EvaluationDTO.EvalInfo addEvalInfo(Work work){
        return EvaluationDTO.EvalInfo.builder()
                .evalBlockId(work.getWorkId())
                .deptName(work.getDepartment().getDeptName())
                .workName(work.getWorkName())
                .build();
    }

    @Transactional
    @Override
    public Evaluation update(Long evalId,int score,String comment){
        Evaluation evaluation = evaluationRepository.findOne(evalId);
        evaluation.change(score,comment);
        return evaluation;
    }
}
