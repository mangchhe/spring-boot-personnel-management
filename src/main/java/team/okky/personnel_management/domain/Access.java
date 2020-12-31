package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
public class Access {
    @Id @GeneratedValue
    private Long access_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date access_date;
    private String access_area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mn_id")
    private Manager manager;
}
