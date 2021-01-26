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
public class Vacation{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vac_id")
    private Long vacId;
    private String vacType;
    private int vacDays;
    private LocalDate vacStartDate;
    private LocalDate vacEndDate;

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;
}
