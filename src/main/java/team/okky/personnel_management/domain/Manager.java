package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Manager {
    @Id @GeneratedValue
    private Long mn_id;
    private String mn_email;
    private String mn_pw;
    private String mn_authority;

    @Temporal(TemporalType.TIMESTAMP)
    private Date mn_create_date;
}
