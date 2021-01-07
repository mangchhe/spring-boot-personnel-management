package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Evaluation {

    @Id @GeneratedValue
    Long eval_id;
    int eval_result_score;
    String eval_comment;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "work_id")
    Work work;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "emp_id")
    Employee employee;

}
