package team.okky.personnel_management.dto;

import lombok.*;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SalaryDTO {
    private String empName;
    private String deptName;
    private String empPosition;
    private int salary;
    private int incentive;

    @Builder
    @Getter @Setter
    public static class SalaryForm{
        private List<DepartmentDTO.Name> department;
        private List<String> position;
        private List<EmployeeDTO.employeeDTO> employee;
        private int salary;
        private int incentive;
    }
}
