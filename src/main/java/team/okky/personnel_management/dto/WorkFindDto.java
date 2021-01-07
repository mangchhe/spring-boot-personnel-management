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

    private Long work_id;
    private String work_name;
    private String work_charge_name;
    private LocalDate work_start_date;
    private LocalDate work_end_date;
    private String dept_name;
    private List<String> employees = new ArrayList<>();
    private String workStatus;

    public WorkFindDto(Work entity) {
        this.work_id = entity.getWork_id();
        this.work_name = entity.getWork_name();
        this.work_charge_name = entity.getWork_charge_name();
        this.work_start_date = entity.getWork_start_date();
        this.work_end_date = entity.getWork_end_date();
        this.dept_name = entity.getDepartment().getDept_name();
        this.employees = findEmpName(entity);
        this.workStatus = findStatus(work_start_date, work_end_date);
    }

    private String findStatus(LocalDate work_start_date, LocalDate work_end_date) {
        LocalDate today = LocalDate.now();
        if(today.isAfter(work_start_date) && today.isBefore(work_end_date)){
            return "진행중";
        }
        else if(today.isAfter(work_end_date)){
            return "완료";
        }
        else return "예정";
    }

    public List<String> findEmpName(Work entity){
        List<Employee> list = entity.getEmployees();
        List<String> empName = new ArrayList<>();
        for(Employee e : list ) {
            empName.add(e.getEmp_name());
        }
        return empName;
    }

}
