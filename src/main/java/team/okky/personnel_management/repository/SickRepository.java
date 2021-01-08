package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Sick;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SickRepository {

    private final EntityManager em;

    public Sick save(Sick sick){
        em.persist(sick);
        return sick;
    }

    public Sick findOne(Long id){
        return em.find(Sick.class, id);
    }

    public List<Sick> findAll(){
        return em.createQuery("select s from Sick s")
                .getResultList();
    }

    public Sick remove(Sick sick){
        em.remove(sick);
        return sick;
    }

    public List<Sick> findAllByDate(LocalDate date){
        return em.createQuery("select s from Sick s where s.sick_start_date <= :date and s.sick_end_date >= :date", Sick.class)
                .setParameter("date", date)
                .getResultList();
    }
}
