package team.okky.personnel_management.work;

import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.department.Department;

import java.time.LocalDate;
import java.util.List;


public class WorkForm {

    @Getter @Setter
    public static class WorkAndDept{
        private Work work;
        private List<Department> departmentList;
    }

    @Getter @Setter
    public static class WorkCreateForm{
        private String workName;
        private Long workDept;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
    }

    @Getter @Setter
    public static class WorkUpdateForm{
        private String workName;
        private String defaultDeptName;
        private String workChargeName;
        private LocalDate workStartDate;
        private LocalDate workEndDate;
        private List<Department> departmentList;
    }

}
