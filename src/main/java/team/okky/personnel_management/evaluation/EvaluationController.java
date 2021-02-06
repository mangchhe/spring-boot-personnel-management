package team.okky.personnel_management.evaluation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.work.WorkService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;
    private final WorkService workService;

    @GetMapping("/evaluation")
    public EvaluationDTO.IndexPage list(@RequestParam("nameType") String nameType,
                                              @RequestParam("name") String name,@RequestParam(value = "page", defaultValue = "1") Integer pageNo) {
        List<EvaluationDTO.EvalBlock> list = null;
        PageResultDTO pageResultDTO = null;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        if(nameType.equals("workName") && !name.isEmpty()){
            list = evaluationService.viewAllByWorkName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByWorkNameForPage(name,pageNo);
        }
        else if(nameType.equals("deptName") && !name.isEmpty()){
            list = evaluationService.viewAllByDeptName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByWorkNameForPage(name,pageNo);
        }
        else if(nameType.equals("empName") && !name.isEmpty()){
            list = evaluationService.viewAllByEmpName(name,pageRequestDTO);
            pageResultDTO = workService.viewAllByEmpNameForPage(name,pageNo);
        }
        else{
            list = evaluationService.viewAll(pageRequestDTO);
            pageResultDTO = workService.viewAllForPage(pageNo);
        }
        return EvaluationDTO.IndexPage.builder()
                .list(list)
                .pageResultDTO(pageResultDTO)
                .build();
    }

    @GetMapping("/evaluation/{evalBlockId}/edit")
    public EvaluationDTO.EvalBlock updateEvalForm(@PathVariable("evalBlockId") Long evalBlockId){
        EvaluationDTO.EvalBlock evalBlock= evaluationService.findByWorkName(workService.findOne(evalBlockId).getWorkName()).get(0);
        return EvaluationDTO.EvalBlock.builder()
                .evalInfo(evalBlock.getEvalInfo())
                .evalListPerWork(evalBlock.getEvalListPerWork())
                .build();
    }

    @PutMapping("/evaluation/{evalBlockId}/edit")
    public String update(@RequestBody EvaluationDTO.EvalPerWork evalPerWork) {
        evaluationService.update(evalPerWork.getEvalId(),evalPerWork.getScore(),evalPerWork.getComment());
        return "redirect:/evaluation";
    }
}



