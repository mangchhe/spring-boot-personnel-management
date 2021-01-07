package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id @GeneratedValue
    private Long emp_id;
    private String emp_jumin;
    private String emp_position;
    private String emp_name;
    private String emp_phone_num;
    private String emp_internal_num;
    private LocalDate emp_join_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="work_id")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="dept_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mn_id")
    private Manager manager;

    public void setWork(Work work) {
        this.work = work;
    }
}
