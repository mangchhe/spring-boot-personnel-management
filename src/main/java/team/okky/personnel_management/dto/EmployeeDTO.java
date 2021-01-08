package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class EmployeeDTO {

    @Builder
    @Getter @Setter
    public static class ListByStatus {
        private String empPosition;
        private String empName;
        private String empInternalNum;
        private LocalDate empJoinDate;
        private String deptName;
    }
    @Builder
    @Getter @Setter
    @EqualsAndHashCode
    public static class DuplicationName {
        private Long empId;
        private String empPosition;
        private String empName;
        private String empInternalNum;
        private LocalDate empJoinDate;
        private String deptName;
    }
}
