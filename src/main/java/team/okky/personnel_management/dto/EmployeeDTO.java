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
        private String emp_position;
        private String emp_name;
        private String emp_internal_num;
        private LocalDate emp_join_date;
        private String dept_name;
    }
    @Builder
    @Getter @Setter
    @EqualsAndHashCode
    public static class DuplicationName {
        private Long emp_id;
        private String emp_position;
        private String emp_name;
        private String emp_internal_num;
        private LocalDate emp_join_date;
        private String dept_name;
    }
}
