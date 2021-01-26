package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Access {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long accessId;

    private LocalDateTime accessDate;
    private String accessArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mn_id")
    private Manager manager;
}
