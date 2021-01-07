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
    private Long work_id;
    private String work_name;
    private String work_charge_name;
    private LocalDate work_start_date;
    private LocalDate work_end_date;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    Department department;

    @OneToMany(mappedBy = "work")
    List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        employees.add(employee);
        employee.setWork(this);
    }

    public void change(String workName, String chargeName, LocalDate startDate, LocalDate endDate, Department department) {
        work_name = workName;
        work_charge_name = chargeName;
        work_start_date = startDate;
        work_end_date = endDate;
        this.department = department;
    }
}
