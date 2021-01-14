package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Evaluation;
import team.okky.personnel_management.dto.EvaluationDTO;
import team.okky.personnel_management.dto.SearchDTO;

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

    public List<Evaluation> findAll(Long workId){
        return em.createQuery("select e from Evaluation e join Work w on e.work = w and w.workId = :workId")
                .setParameter("workId",workId)
                .getResultList();
    }

    public List<Evaluation> findByWorkName(String workName){
        return em.createQuery("select e from Evaluation e join Work w on e.work = w and w.workName = :workName")
                .setParameter("workName",workName)
                .getResultList();
    }

    public List<Evaluation> findByDeptName(String deptName){
        return em.createQuery("select e from Evaluation e inner join Work w on e.work = w inner join Department d on w.department = d and d.deptName =:deptName")
                .setParameter("deptName",deptName)
                .getResultList();
    }

    public List<Evaluation> findByEmpName(String empName){
        return em.createQuery("select e from Evaluation e join Employee em on e.employee = em and em.empName =: empName")
                .setParameter("empName",empName)
                .getResultList();
    }


    public Evaluation remove(Evaluation evaluation){
        em.remove(evaluation);
        return evaluation;
    }
}
