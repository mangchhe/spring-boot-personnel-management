package team.okky.personnel_management.employee;

import lombok.*;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Index {
        private Long empId;
        private String empName;
        private String deptName;
        private String empPosition;
        private LocalDate empJoinDate;
        private String empPhoneNum;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class IndexWithPage {
        List<Index> list;
        PageResultDTO pageResultDTO;
    }

    @Builder
    @Getter @Setter
    public static class AddForm {
        private String empName;
        private Long deptId;
        private String empPosition;
        private LocalDate empJoinDate;
        private String empPhoneNum;
    }

    @Builder
    @Getter @Setter
    public static class UpdateForm {
        private Long empId;
        private String empPhoneNum;
        private LocalDate empJoinDate;
    }

}
