package team.okky.personnel_management.controller;

import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;

import java.util.List;


public class WorkForm {

    @Getter @Setter
    public static class workAndDept{
        private Work work;
        private List<Department> departmentList;
        private String defaultDeptName;
    }

}
