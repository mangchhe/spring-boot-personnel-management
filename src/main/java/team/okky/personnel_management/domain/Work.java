package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Work {

    @Id @GeneratedValue
    private Long workId;
    private String workName;
    private String workChargeName;
    private LocalDate workStartDate;
    private LocalDate workEndDate;

    @ManyToOne @JoinColumn(name = "dept_id")
    Department department;

    @OneToMany(mappedBy = "work")
    List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        employees.add(employee);
        employee.setWork(this);
    }


    public void change(String workName, String chargeName, LocalDate startDate, LocalDate endDate, Department department) {
        this.workName = workName;
        this.workChargeName = chargeName;
        this.workStartDate = startDate;
        this.workEndDate = endDate;
        this.department = department;
    }
}
