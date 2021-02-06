package team.okky.personnel_management.evaluation;

import lombok.*;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.work.WorkDTO;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDTO {

    @Builder @Getter @Setter
    public static class EvalInfo {
        private Long evalBlockId;
        private String deptName;
        private String workName;
    }

    @Builder @Getter @Setter
    public static class EvalBlock{
        private EvaluationDTO.EvalInfo evalInfo;
        private List<EvalPerWork> evalListPerWork = new ArrayList<>();
    }

    @Builder @Getter @Setter
    public static class EvalPerWork{
        private Long evalId;
        private String comment;
        private int score;
        private String empName;

    }

    @Builder @Getter
    public static class IndexPage{
        private List<EvaluationDTO.EvalBlock> list;
        private PageResultDTO pageResultDTO;
    }

}
