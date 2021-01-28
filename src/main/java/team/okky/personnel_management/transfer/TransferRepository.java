package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.employee.EmployeePosition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransferRepository {

    private final EntityManager em;

    public Transfer save(Transfer transfer){
        em.persist(transfer);
        return transfer;
    }

    public Transfer findOne(Long id) {
        return em.find(Transfer.class, id);
    }

    public List<Transfer> findAll(){
        return em.createQuery("select t from Transfer t order by t.appointDate desc", Transfer.class)
                .getResultList();
    }

    public Transfer remove(Transfer transfer){
        em.remove(transfer);
        return transfer;
    }

    public List<Transfer> findAllByEmpName(String empName){
        return em.createQuery("select t from Transfer t where t.employee.empName = :empName order by t.appointDate desc", Transfer.class)
                .setParameter("empName", empName)
                .getResultList();
    }

    public List<Transfer> findAllByDeptName(String deptName){
        return em.createQuery("select t from Transfer t where t.curDepartment.deptName = :deptName order by t.appointDate desc", Transfer.class)
                .setParameter("deptName", deptName)
                .getResultList();
    }

    public List<Transfer> findAllByEmpPosition(String empPosition){
        EmployeePosition position = EmployeePosition.findByEmployeePosition(empPosition);
        return em.createQuery("select t from Transfer t where t.curPosition = :position order by t.appointDate desc", Transfer.class)
                .setParameter("position", position)
                .getResultList();
    }
}
