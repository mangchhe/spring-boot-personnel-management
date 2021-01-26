package team.okky.personnel_management.access;

import lombok.*;
import team.okky.personnel_management.manager.Manager;

import javax.persistence.*;
import java.time.LocalDateTime;

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
