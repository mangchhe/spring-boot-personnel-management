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
    @Column(name = "access_id")
    private Long accessId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accessDate;
    private String accessArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mn_id")
    private Manager manager;
}
