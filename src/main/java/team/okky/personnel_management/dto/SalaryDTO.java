package team.okky.personnel_management.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SalaryDTO {
    private Long empId;
    private String empName;
    private String deptName;
    private String empPosition;
    private int salary;
    private int incentive;

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
    public static class SalaryList{
        private Long empId;
        private int salary;
        private int incentive;
    }

}
