package team.okky.personnel_management.evaluation;

import lombok.*;

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
        private Long evalId;
        private String comment;
        private int score;
        private String empName;

    }

}
