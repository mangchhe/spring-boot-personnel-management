package team.okky.personnel_management.transfer;

import lombok.*;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeePosition;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;
    private EmployeePosition curPosition;
    private EmployeePosition transPosition;
    private LocalDate approveDate;
    private LocalDate appointDate;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cur_dept_id")
    private Department curDepartment;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "trans_dept_id")
    private Department transDepartment;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;

    public void changeTransPosition(String transPosition){
        this.transPosition = EmployeePosition.findByEmployeePosition(transPosition);
    }

    public void changeTransDepartment(Department transDepartment){
        this.transDepartment = transDepartment;
    }

    public void changeAppointDate(LocalDate appointDate){
        this.approveDate = LocalDate.now();
        this.appointDate = appointDate;
    }

    @Builder
    public Transfer(String transPosition, String curPosition, LocalDate approveDate, LocalDate appointDate, Department curDepartment, Department transDepartment, Employee employee) {
        this.transPosition = EmployeePosition.findByEmployeePosition(transPosition);
        this.curPosition = EmployeePosition.findByEmployeePosition(curPosition);
        this.approveDate = approveDate;
        this.appointDate = appointDate;
        this.curDepartment = curDepartment;
        this.transDepartment = transDepartment;
        this.employee = employee;
    }

    public TransferDTO.Index entityToIndex(){
        LocalDate nowDate = LocalDate.now();
        return TransferDTO.Index.builder()
                .employeeName(getEmployee().getEmpName())
                .curDepartmentName(getCurDepartment().getDeptName())
                .transferDepartmentName(getTransDepartment().getDeptName())
                .curPosition(getCurPosition().getPosition())
                .transferPosition(getTransPosition().getPosition())
                .approveDate(getApproveDate())
                .appointDate(getAppointDate())
                .isEnd(getAppointDate().isBefore(nowDate) || getAppointDate().isEqual(nowDate))
                .build();
    }
}
