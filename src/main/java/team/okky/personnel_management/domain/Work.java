package team.okky.personnel_management.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Work {

    @Id @GeneratedValue
    Long work_id;
    String work_name;
    String work_change_name;
    @Temporal(TemporalType.TIMESTAMP)
    Date work_start_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date work_end_date;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "dept_id")
    Department department;
}
