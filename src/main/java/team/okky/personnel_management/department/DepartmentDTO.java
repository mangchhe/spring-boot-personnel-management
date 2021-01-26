package team.okky.personnel_management.department;

import lombok.*;

public class DepartmentDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class Name{
        private Long deptId;
        private String deptName;
    }
}
