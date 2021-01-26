package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Access {
    @Id @GeneratedValue
    @Column(name = "access_id")
    private Long accessId;

    private LocalDate accessDate;
    private String accessArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mn_id")
    private Manager manager;
}
