package team.okky.personnel_management.transfer;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TransferRepository {
    @PersistenceContext
    private EntityManager em;

    public Transfer save(Transfer transfer){
        em.persist(transfer);
        return transfer;
    }

    public Transfer findOne(Long id) {
        return em.find(Transfer.class, id);
    }

    public List<Transfer> findAll(){
        return em.createQuery("select t from Transfer t")
                .getResultList();
    }

    public Transfer remove(Transfer transfer){
        em.remove(transfer);
        return transfer;
    }
}
