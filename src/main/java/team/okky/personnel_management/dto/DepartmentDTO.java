package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DepartmentDTO {

    @Builder
    @Getter @Setter
    public static class Name{
        private Long deptId;
        private String deptName;
    }
}
