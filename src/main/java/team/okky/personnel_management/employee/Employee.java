package team.okky.personnel_management.employee;

import lombok.*;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.manager.Manager;
import team.okky.personnel_management.work.Work;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Enumerated(value = EnumType.STRING)
    private EmployeePosition empPosition;
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

    public void changeDepartment(Department department){
        this.department = department;
    }

    public void changePosition(EmployeePosition empPosition){
        this.empPosition = empPosition;
    }

    public void changeWork(Work work) {
        this.work = work;
    }

    public void changePhoneNum(String empPhoneNum){
        this.empPhoneNum = empPhoneNum;
    }

    public void changeJoinDate(LocalDate empJoinDate){
        this.empJoinDate = empJoinDate;
    }

    @Builder
    public Employee(EmployeePosition empPosition, String empName, String empPhoneNum, LocalDate empJoinDate, Work work, Department department, Manager manager) {
        this.empPosition = empPosition == null ? EmployeePosition.DEFAULT : empPosition;
        this.empName = empName;
        this.empPhoneNum = empPhoneNum;
        this.empJoinDate = empJoinDate;
        this.work = work;
        this.department = department;
        this.manager = manager;
    }

    public EmployeeDTO.ListIndex entityToListIndex(){
        return EmployeeDTO.ListIndex.builder()
                .empId(getEmpId())
                .empName(getEmpName())
                .deptName(getDepartment().getDeptName())
                .empPosition(getEmpPosition().getPosition())
                .empJoinDate(getEmpJoinDate())
                .empPhoneNum(getEmpPhoneNum())
                .build();
    }
}
