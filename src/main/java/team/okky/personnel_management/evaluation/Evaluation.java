package team.okky.personnel_management.evaluation;

import lombok.*;
import team.okky.personnel_management.work.Work;
import team.okky.personnel_management.employee.Employee;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_id")
    private Long evalId;
    private int evalResultScore;
    private String evalComment;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    private Employee employee;

    public void change(int score, String comment){
        evalResultScore = score;
        evalComment = comment;
    }
}
