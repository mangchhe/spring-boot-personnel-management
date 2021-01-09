package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Evaluation {

    @Id @GeneratedValue
    @Column(name = "eval_id")
    private Long evalId;
    private int evalResultScore;
    private String evalComment;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;

}
