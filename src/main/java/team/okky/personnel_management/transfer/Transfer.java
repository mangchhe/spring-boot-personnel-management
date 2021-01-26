package team.okky.personnel_management.transfer;

import lombok.Getter;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Transfer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;
    private String transPosition;
    private String transCurPosition;
    private String transCurDept;
    private LocalDate approveDate;
    private LocalDate appointDate;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;
}
