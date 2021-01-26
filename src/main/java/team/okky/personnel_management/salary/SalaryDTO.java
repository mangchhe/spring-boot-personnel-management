package team.okky.personnel_management.salary;

import lombok.*;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;


public class SalaryDTO {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class indexSalary {
        private Long empId;
        private String empName;
        private String deptName;
        private String empPosition;
        private int salary;
        private int incentive;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class indexPage {
        List<indexSalary> list;
        PageResultDTO pageResultDTO;
    }

    @Builder
    @Getter @Setter
    public static class SalaryForm{
        private String deptName;
        private String position;
        private String empName;
        private int salary;
        private int incentive;
    }

    @Builder
    @Getter @Setter
    public static class updateForm{
        private Long empId;
        private int salary;
        private int incentive;
    }

}
