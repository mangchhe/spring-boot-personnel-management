package team.okky.personnel_management.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sick {

    @Id @GeneratedValue
    @Column(name = "sick_id")
    private Long sickId;

    private String sickTitle;
    private String sickContent;
    private LocalDate sickStartDate;
    private LocalDate sickEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;

}
