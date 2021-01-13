package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.domain.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDTO {

    @Builder @Getter @Setter
    public static class EvalInfo {
        private Long evalBlockId;
        private String deptName;
        private String workName;
    }

    @Getter @Setter
    public static class evalBlock{
        private EvaluationDTO.EvalInfo evalInfo;
        private List<evalPerWork> evalPerWorkList = new ArrayList<>();
    }

    @Builder @Getter @Setter
    public static class evalPerWork{
        private Long empId;
        private String comment;
        private int score;
        private String empName;

    }

}
