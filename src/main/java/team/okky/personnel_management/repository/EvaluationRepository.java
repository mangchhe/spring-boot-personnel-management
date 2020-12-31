package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Evaluation;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EvaluationRepository {

    private final EntityManager em;

    public Evaluation save(Evaluation evaluation){
        em.persist(evaluation);
        return evaluation;
    }

    public Evaluation findOne(Long id){
        return em.find(Evaluation.class, id);
    }

    public List<Evaluation> findAll(){
        return em.createQuery("select e from Evaluation e")
                .getResultList();
    }

    public Evaluation remove(Evaluation evaluation){
        em.remove(evaluation);
        return evaluation;
    }
}
