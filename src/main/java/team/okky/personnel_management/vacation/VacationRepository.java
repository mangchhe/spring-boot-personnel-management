package team.okky.personnel_management.vacation;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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
        return em.createQuery("select v from Vacation v", Vacation.class)
                .getResultList();
    }

    public Vacation remove(Vacation vacation){
        em.remove(vacation);
        return vacation;
    }

    public List<Vacation> findAllByDate(LocalDate localDate){
        return em.createQuery("select v from Vacation v where v.vacStartDate <= :date and v.vacEndDate >= :date", Vacation.class)
                .setParameter("date", localDate)
                .getResultList();
    }
}
