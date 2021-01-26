package team.okky.personnel_management.evaluation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.work.Work;
import team.okky.personnel_management.utils.dto.SearchDTO;
import team.okky.personnel_management.work.WorkRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final WorkRepository workRepository;

    @Transactional
    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public Evaluation findOne(Long id) {
        return evaluationRepository.findOne(id);
    }

    public EvaluationDTO.evalBlock findOneByEvalBlock(Long evalBlockId){
        SearchDTO evalSearch = new SearchDTO();
        evalSearch.setNameType("workName");
        Work work = workRepository.findOne(evalBlockId);
        evalSearch.setName(work.getWorkName());
        return findAll(evalSearch).get(0);
    }

    public List<EvaluationDTO.evalBlock> findAll(SearchDTO evalSearch) {
        List<EvaluationDTO.evalBlock> list = new ArrayList<>();
        EvaluationDTO.evalBlock evalBlock = new EvaluationDTO.evalBlock();
        List<EvaluationDTO.evalPerWork> evalPerWorkList = new ArrayList<>();
        List<Long> workIdList = workRepository.findWorkId();

        String nameType = evalSearch.getNameType();
        String name = evalSearch.getName();


        if(nameType.equals("empName")&& !name.isEmpty()) {
            List<Evaluation> evalByEmpName = evaluationRepository.findByEmpName(name);

            for (Evaluation e : evalByEmpName) {
                List<EvaluationDTO.evalPerWork> findEvalPerWork = new ArrayList<>();
                findEvalPerWork.add(addEvalPerWorkList(e));

                Work work = workRepository.findOne(e.getWork().getWorkId());
                EvaluationDTO.EvalInfo evalInfo = addEvalInfo(work);

                EvaluationDTO.evalBlock evalBlockAll = new EvaluationDTO.evalBlock();
                evalBlockAll.setEvalInfo(evalInfo);
                evalBlockAll.setEvalPerWorkList(findEvalPerWork);
                list.add(evalBlockAll);
            }
        }
        else if(nameType.equals("workName")&& !name.isEmpty()){
            List<Evaluation> evalByWorkName = evaluationRepository.findByWorkName(name);
            for (Evaluation e : evalByWorkName) {
                evalPerWorkList.add(addEvalPerWorkList(e));
            }

            Work work = workRepository.findOne(evalByWorkName.get(0).getWork().getWorkId());
            EvaluationDTO.EvalInfo evalInfo = addEvalInfo(work);

            evalBlock.setEvalInfo(evalInfo);
            evalBlock.setEvalPerWorkList(evalPerWorkList);
            list.add(evalBlock);
        }
        else if(nameType.equals("deptName")&& !name.isEmpty()){
            List<Work> workByDeptName = workRepository.findByDeptName(name);

            for (Work w : workByDeptName) {
                List<EvaluationDTO.evalPerWork> evalPerWorkById = new ArrayList<>();
                for (Evaluation e : evaluationRepository.findByWorkName(w.getWorkName())) {
                   evalPerWorkById.add(addEvalPerWorkList(e));
                }

                Work work = workRepository.findOne(w.getWorkId());
                EvaluationDTO.EvalInfo evalInfo = addEvalInfo(work);

                EvaluationDTO.evalBlock evalBlockAll = new EvaluationDTO.evalBlock();
                evalBlockAll.setEvalInfo(evalInfo);
                evalBlockAll.setEvalPerWorkList(evalPerWorkById);
                list.add(evalBlockAll);
            }
        }
        else {
            for (Long workId : workIdList) {
                List<EvaluationDTO.evalPerWork> evalPerWorkById = new ArrayList<>();
                for (Evaluation e : evaluationRepository.findAll(workId)) {
                    evalPerWorkById.add(addEvalPerWorkList(e));
                }

                Work work = workRepository.findOne(workId);
                EvaluationDTO.EvalInfo evalInfo = addEvalInfo(work);

                EvaluationDTO.evalBlock evalBlockAll = new EvaluationDTO.evalBlock();
                evalBlockAll.setEvalInfo(evalInfo);
                evalBlockAll.setEvalPerWorkList(evalPerWorkById);
                list.add(evalBlockAll);
            }
        }
    return list;
    }

    public EvaluationDTO.evalPerWork addEvalPerWorkList(Evaluation e){
        EvaluationDTO.evalPerWork evalPerWork = EvaluationDTO.evalPerWork.builder()
                .evalId(e.getEvalId())
                .empName(e.getEmployee().getEmpName())
                .score(e.getEvalResultScore())
                .comment(e.getEvalComment())
                .build();
        return evalPerWork;
    }

    public EvaluationDTO.EvalInfo addEvalInfo(Work work){
        EvaluationDTO.EvalInfo evalInfo = EvaluationDTO.EvalInfo.builder()
                .evalBlockId(work.getWorkId())
                .deptName(work.getDepartment().getDeptName())
                .workName(work.getWorkName())
                .build();
        return evalInfo;
    }

    @Transactional
    public Evaluation update(Long evalId,int score,String comment){
        Evaluation evaluation = evaluationRepository.findOne(evalId);
        evaluation.change(score,comment);
        return evaluation;
    }
}
