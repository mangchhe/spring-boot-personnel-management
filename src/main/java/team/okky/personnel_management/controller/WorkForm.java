package team.okky.personnel_management.controller;

import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.domain.Department;

import java.time.LocalDate;

@Getter @Setter
public class WorkForm {
    private String work_name;
    private String work_charge_name;
    private LocalDate work_start_date;
    private LocalDate work_end_date;
    private String deptName;
}
