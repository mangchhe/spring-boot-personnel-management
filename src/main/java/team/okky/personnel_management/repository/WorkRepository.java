package team.okky.personnel_management.repository;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Work;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class WorkRepository {
    @PersistenceContext
    private EntityManager em;

    public Work save(Work work){
        em.persist(work);
        return work;
    }

    public Work findOne(Long id) {
        return em.find(Work.class, id);
    }

    public List<Work> findAll(){
        return em.createQuery("select w from Work w")
                .getResultList();
    }

    public Work remove(Work work){
        em.remove(work);
        return work;
    }
}
