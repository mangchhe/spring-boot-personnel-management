package team.okky.personnel_management.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkFindDto {

    private Long workId;
    private String workName;
    private String workChargeName;
    private LocalDate workStartDate;
    private LocalDate workEndDate;
    private String deptName;
    private String employees;
    private Boolean workStatus;

    public WorkFindDto(Work entity) {
        this.workId = entity.getWorkId();
        this.workName = entity.getWorkName();
        this.workChargeName = entity.getWorkChargeName();
        this.workStartDate = entity.getWorkStartDate();
        this.workEndDate = entity.getWorkEndDate();
        this.deptName = entity.getDepartment().getDeptName();
        this.employees = findEmpName(entity);
        this.workStatus = findStatus(workStartDate, workEndDate);
    }

    private Boolean findStatus(LocalDate workStartDate, LocalDate workEndDate) {
        LocalDate today = LocalDate.now();
        if(today.isAfter(workStartDate)&& (today.isBefore(workEndDate)) || today.equals(workStartDate) || today.equals(workEndDate)){
            return true;
        }
        else {
            return false;
        }
    }

    public String findEmpName(Work entity){
        List<Employee> list = entity.getEmployees();
        String empName="";
        for(Employee e : list ) {
            empName = empName+" "+e.getEmpName();
        }
        return empName;
    }

}
