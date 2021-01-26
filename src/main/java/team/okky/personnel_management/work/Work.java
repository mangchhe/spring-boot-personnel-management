package team.okky.personnel_management.work;

import lombok.*;
import team.okky.personnel_management.department.Department;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Work {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workId;
    private String workName;
    private String workChargeName;
    private LocalDate workStartDate;
    private LocalDate workEndDate;

    @ManyToOne @JoinColumn(name = "dept_id")
    Department department;

    public void change(String workName, String chargeName, LocalDate startDate, LocalDate endDate, Department department) {
        this.workName = workName;
        this.workChargeName = chargeName;
        this.workStartDate = startDate;
        this.workEndDate = endDate;
        this.department = department;
    }
}
