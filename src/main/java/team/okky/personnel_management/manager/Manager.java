package team.okky.personnel_management.manager;

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mn_id")
    private Long mnId;

    @Column(unique = true)
    private String mnEmail;
    private String mnPw;
    private String mnAuthority;

    private LocalDate mnCreateDate;
}
