package team.okky.personnel_management.work;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    public List<Work> findByWorkName(String workName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select w from Work w where w.workName = :name",Work.class)
                .setParameter("name",workName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByWorkName(String workName){
        return em.createQuery("select count(w) from Work w where w.workName = :name",Long.class)
                .setParameter("name",workName)
                .getSingleResult().intValue();
    }

    public List<Work> findByEmpName(String empName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select e.work from Evaluation e join e.employee " +
                "on e.employee.empName =: name order by e.work.workEndDate desc",Work.class)
                .setParameter("name",empName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByEmpName(String empName){
        return em.createQuery("select count(e.work) from Evaluation e join e.employee on e.employee.empName =: name",Long.class)
                .setParameter("name",empName)
                .getSingleResult().intValue();
    }

    public List<Work> findByDeptName(String deptName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select w from Work w join fetch w.department where w.department.deptName = :name")
                .setParameter("name",deptName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public List<Work> findByDeptName(String deptName){
        return em.createQuery("select w from Work w join fetch w.department where w.department.deptName = :name")
                .setParameter("name",deptName)
                .getResultList();
    }

    public int findTotalByDeptName(String deptName){
        return em.createQuery("select count(w) from Work w join fetch w.department where w.department.deptName = :name",Long.class)
                .setParameter("name",deptName)
                .getSingleResult().intValue();
    }

    public List<Work> findAll(PageRequestDTO pageRequestDTO) {
        return em.createQuery("select w from Work w order by w.workEndDate desc")
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public List<Work> findAll() {
        return em.createQuery("select w from Work w order by w.workEndDate desc")
                .getResultList();
    }

    public int findTotal(){
        return em.createQuery("select count(w) from Work w",Long.class)
                .getSingleResult().intValue();
    }

    public List<Long> findWorkId(){
        return em.createQuery("select w.workId from Work w")
                .getResultList();
    }

    public Work remove(Work work){
        em.remove(work);
        return work;
    }
}
