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
    @Column(name = "emp_id")
    private Long empId;
    private String empPosition;
    private String empName;
    private String empPhoneNum;
    private LocalDate empJoinDate;

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

    public void changePhoneNum(String empPhoneNum){
        this.empPhoneNum = empPhoneNum;
    }

    public void changeJoinDate(LocalDate empJoinDate){
        this.empJoinDate = empJoinDate;
    }
}
