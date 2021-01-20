package team.okky.personnel_management.repository;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Manager;
import team.okky.personnel_management.domain.Vacation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ManagerRepository {
    @PersistenceContext
    private EntityManager em;

    public Manager save(Manager manager){
        em.persist(manager);
        return manager;
    }

    public Manager findOne(Long id) {
        return em.find(Manager.class, id);
    }

    public List<Manager> findAll(){
        return em.createQuery("select m from Manager m", Manager.class)
                .getResultList();
    }

    public Manager remove(Manager manager){
        em.remove(manager);
        return manager;
    }

    public List<Manager> findByEmail(String email){
        return em.createQuery("select m from Manager m where m.mnEmail = :email", Manager.class)
                .setParameter("email", email)
                .getResultList();
    }
}
