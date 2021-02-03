package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.employee.EmployeePosition;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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


    public Transfer remove(Transfer transfer){
        em.remove(transfer);
        return transfer;
    }

    public List<Transfer> findAll(PageRequestDTO pageRequestDTO){
        return em.createQuery("select t from Transfer t left join fetch t.employee left join fetch t.curDepartment left join fetch t.transDepartment order by t.appointDate desc", Transfer.class)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotal(){
        return em.createQuery("select count(t) from Transfer t", Long.class)
                .getSingleResult().intValue();
    }

    public List<Transfer> findAllByEmpName(String empName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select t from Transfer t join fetch t.employee join fetch t.curDepartment join fetch t.transDepartment where t.employee.empName = :empName order by t.appointDate desc", Transfer.class)
                .setParameter("empName", empName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByEmpName(String empName){
        return em.createQuery("select count(t) from Transfer t where t.employee.empName = :empName", Long.class)
                .setParameter("empName", empName)
                .getSingleResult().intValue();
    }

    public List<Transfer> findAllByDeptName(String deptName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select t from Transfer t join fetch t.employee join fetch t.curDepartment join fetch t.transDepartment where t.curDepartment.deptName = :deptName order by t.appointDate desc", Transfer.class)
                .setParameter("deptName", deptName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByDeptName(String deptName){
        return em.createQuery("select count(t) from Transfer t where t.curDepartment.deptName = :deptName", Long.class)
                .setParameter("deptName", deptName)
                .getSingleResult().intValue();
    }

    public List<Transfer> findAllByEmpPosition(String empPosition, PageRequestDTO pageRequestDTO){
        EmployeePosition position = EmployeePosition.findByEmployeePosition(empPosition);
        return em.createQuery("select t from Transfer t join fetch t.employee join fetch t.curDepartment join fetch  t.transDepartment where t.curPosition = :position order by t.appointDate desc", Transfer.class)
                .setParameter("position", position)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByEmpPosition(String empPosition){
        EmployeePosition position = EmployeePosition.findByEmployeePosition(empPosition);
        return em.createQuery("select count(t) from Transfer t where t.curPosition = :position", Long.class)
                .setParameter("position", position)
                .getSingleResult().intValue();
    }

    public List<Transfer> findAllByNowAppointDate(){
        return em.createQuery("select t from Transfer t join fetch t.employee where t.appointDate = :nowDate", Transfer.class)
                .setParameter("nowDate", LocalDate.now())
                .getResultList();
    }

}
