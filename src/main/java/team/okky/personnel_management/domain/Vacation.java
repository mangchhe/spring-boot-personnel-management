package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Vacation{

    @Id @GeneratedValue
    private Long vac_id;
    private String vac_type;
    private String vac_days;
    @Temporal(TemporalType.TIMESTAMP)
    private Date vac_start_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Date vac_end_date;

    @ManyToOne()
    @JoinColumn(name="emp_id")
    private Employee employee;
}
