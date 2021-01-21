package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.dto.EvaluationDTO;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.service.EvaluationService;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @GetMapping("/evaluation")
    public List<EvaluationDTO.evalBlock> list(@RequestParam("nameType") String nameType,
                                              @RequestParam("name") String name){
        SearchDTO evalSearch = new SearchDTO();
        evalSearch.setNameType(nameType);
        evalSearch.setName(name);
        return evaluationService.findAll(evalSearch);
    }

    @GetMapping("/evaluation/{evalBlockId}/edit")
    public EvaluationDTO.evalBlock updateEvalForm(@PathVariable("evalBlockId") Long evalBlockId){
        EvaluationDTO.evalBlock evalBlock= evaluationService.findOneByEvalBlock(evalBlockId);
        EvaluationDTO.evalBlock form = new EvaluationDTO.evalBlock();
        form.setEvalInfo(evalBlock.getEvalInfo());
        form.setEvalPerWorkList(evalBlock.getEvalPerWorkList());

        return form;
    }

    @PutMapping("/evaluation/{evalBlockId}/edit")
    public String update(@PathVariable("evalBlockId") Long evalBlockId,@RequestBody EvaluationDTO.evalPerWork evalPerWork) {
        evaluationService.update(evalPerWork.getEvalId(),evalPerWork.getScore(),evalPerWork.getComment());
        return "redirect:/evaluation";
    }
}



