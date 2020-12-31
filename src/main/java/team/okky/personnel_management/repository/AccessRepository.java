package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Access;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccessRepository {

    private final EntityManager em;

    public Access save(Access access){
        em.persist(access);
        return access;
    }

    public Access findOne(Long id){
        return em.find(Access.class, id);
    }

    public List<Access> findAll(){
        return em.createQuery("select a from Access a")
                .getResultList();
    }

    public Access remove(Access access){
        em.remove(access);
        return access;
    }
}
