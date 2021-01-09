package team.okky.personnel_management.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Transfer {

    @Id @GeneratedValue
    @Column(name = "trans_id")
    private Long transId;
    private String transDept;
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointDate;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;
}
