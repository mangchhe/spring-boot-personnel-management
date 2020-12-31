package team.okky.personnel_management.domain;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
public class Attendance {
    @Id @GeneratedValue
    private Long att_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date att_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Date att_go;
    @Temporal(TemporalType.TIMESTAMP)
    private Date att_off;
    private String att_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id")
    private Employee employee;
}
