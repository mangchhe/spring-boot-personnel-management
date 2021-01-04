package team.okky.personnel_management.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Sick {

    @Id @GeneratedValue
    private Long sick_id;

    private String sick_title;
    private String sick_content;
    private LocalDate sick_start_date;
    private LocalDate sick_end_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;

}
