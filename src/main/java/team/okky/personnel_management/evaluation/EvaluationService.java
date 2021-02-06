package team.okky.personnel_management.evaluation;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.work.Work;
import team.okky.personnel_management.work.WorkDTO;

import java.util.List;

public interface EvaluationService {
    public Evaluation save(Evaluation evaluation);
    public Evaluation findOne(Long id);
    public List<EvaluationDTO.EvalBlock> findByWorkName(String name);

    public List<EvaluationDTO.EvalBlock> viewAllByWorkName(String name, PageRequestDTO pageRequestDTO);
    public List<EvaluationDTO.EvalBlock> viewAllByDeptName(String name,PageRequestDTO pageRequestDTO);
    public List<EvaluationDTO.EvalBlock> viewAllByEmpName(String name,PageRequestDTO pageRequestDTO);
    public List<EvaluationDTO.EvalBlock> viewAll(PageRequestDTO pageRequestDTO);

    public List<EvaluationDTO.EvalBlock> addEvalBlock(List<WorkDTO.indexWork> workList);
    public EvaluationDTO.EvalPerWork addEvalListPerWork(Evaluation e);
    public EvaluationDTO.EvalInfo addEvalInfo(Work work);

    public Evaluation update(Long evalId,int score,String comment);
}

