package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public List<EvaluationDTO.evalBlock> list(){


        return evaluationService.findAll();
    }


}
