package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    @Id @GeneratedValue
    @Column(name = "mn_id")
    private Long mnId;
    private String mnEmail;
    private String mnPw;
    private String mnAuthority;

    @Temporal(TemporalType.TIMESTAMP)
    private Date mnCreateDate;
}
