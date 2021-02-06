package team.okky.personnel_management.work;

import lombok.*;
import team.okky.personnel_management.department.DepartmentDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;

public class WorkDTO {

    @Builder @Getter
    public static class indexWork {
        private Long workId;
        private String workName;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
        private String deptName;
        private String employees;
        private Boolean workStatus;
    }

    @Builder @Getter
    public static class IndexPage{
        private List<indexWork> list;
        private PageResultDTO pageResultDTO;
    }

    @Builder @Getter
    public static class WorkCreateForm{
        private String workName;
        private Long workDept;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
    }

    @Builder @Getter
    public static class WorkUpdateForm{
        private String workName;
        private String defaultDeptName;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
        private List<DepartmentDTO.Name> departmentList;
    }

}

