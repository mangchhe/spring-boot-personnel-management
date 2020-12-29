package team.okky.personnel_management.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Transfer {

    @Id @GeneratedValue
    Long trans_id;
    String trans_dept;
    @Temporal(TemporalType.TIMESTAMP)
    Date appoint_date;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    Department department;
}
