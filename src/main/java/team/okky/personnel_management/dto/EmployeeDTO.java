package team.okky.personnel_management.dto;

import lombok.*;
import team.okky.personnel_management.domain.Department;

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
        private LocalDate empJoinDate;
        private String deptName;
        private String empInternalNum;
    }

    @Builder
    @Getter @Setter
    public static class AddEmployee {
        private String empName;
        private Long deptId;
        private String empPosition;
        private LocalDate empJoinDate;
        private String empPhoneNum;
    }

    @Builder
    @Getter @Setter
    public static class UpdateEmployee {
        private Long empId;
        private String empPhoneNum;
        private LocalDate empJoinDate;
    }

    @Getter @Setter
    public static class ListIndex {
        private Long empId;
        private String empName;
        private String departmentDeptName;
        private String empPosition;
        private LocalDate empJoinDate;
        private String empPhoneNum;
        private String empInternalNum;
    }
}
