package team.okky.personnel_management.transfer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;
    private String curPosition;
    private String transPosition;
    private LocalDate approveDate;
    private LocalDate appointDate;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cur_dept_id")
    private Department curDepartment;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "trans_dept_id")
    private Department transDepartment;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;

    @Builder
    public Transfer(String transPosition, String curPosition, LocalDate approveDate, LocalDate appointDate, Department curDepartment, Department transDepartment, Employee employee) {
        this.transPosition = transPosition;
        this.curPosition = curPosition;
        this.approveDate = approveDate;
        this.appointDate = appointDate;
        this.curDepartment = curDepartment;
        this.transDepartment = transDepartment;
        this.employee = employee;
    }

    public TransferDTO.Info entityToInfo(){
        return TransferDTO.Info.builder()
                .employeeName(getEmployee().getEmpName())
                .curDepartmentName(getCurDepartment().getDeptName())
                .transferDepartmentName(getTransDepartment().getDeptName())
                .curPosition(getCurPosition())
                .transferPosition(getTransPosition())
                .approveDate(getApproveDate())
                .appointDate(getAppointDate())
                .build();
    }
}
