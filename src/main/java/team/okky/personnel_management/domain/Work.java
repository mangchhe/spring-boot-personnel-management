package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Work {

    @Id @GeneratedValue
    @Column(name = "work_id")
    private Long workId;
    private String workName;
    private String workChargeName;
    private LocalDate workStartDate;
    private LocalDate workEndDate;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    private Department department;

    @OneToMany(mappedBy = "work")
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        employees.add(employee);
        employee.setWork(this);
    }

    public Work(String workName, String workChargeName, LocalDate workStartDate, LocalDate workEndDate, Department department) {
        this.workName = workName;
        this.workChargeName = workChargeName;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.department = department;
    }
}
