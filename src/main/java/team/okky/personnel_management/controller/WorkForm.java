package team.okky.personnel_management.controller;

import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;

import java.time.LocalDate;
import java.util.List;


public class WorkForm {

    @Getter @Setter
    public static class workAndDept{
        private Work work;
        private List<Department> departmentList;
        private String defaultDeptName;
    }

    @Getter @Setter
    public static class workCreateForm{
        private String workName;
        private Long workDept;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
    }


}
