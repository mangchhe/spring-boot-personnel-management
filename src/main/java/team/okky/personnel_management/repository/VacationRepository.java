package team.okky.personnel_management.repository;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Transfer;
import team.okky.personnel_management.domain.Vacation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VacationRepository {
    @PersistenceContext
    private EntityManager em;

    public Vacation save(Vacation vacation){
        em.persist(vacation);
        return vacation;
    }

    public Vacation findOne(Long id) {
        return em.find(Vacation.class, id);
    }

    public List<Vacation> findAll(){
        return em.createQuery("select v from Vacation v")
                .getResultList();
    }

    public Vacation remove(Vacation vacation){
        em.remove(vacation);
        return vacation;
    }
}
