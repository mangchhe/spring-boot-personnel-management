package team.okky.personnel_management.repository;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.dto.WorkFindDto;

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

    public Work findByWorkName(String workName){
        TypedQuery<Work> query = em.createQuery("select w from Work w where w.workName = :name",Work.class)
                .setParameter("name",workName);
        return query.getSingleResult();
    }

    public Work findByEmpName(String empName){
        TypedQuery<Work> query = em.createQuery("select w from Work w join Employee e on w = e.work and e.empName = :name",Work.class)
                .setParameter("name",empName);
        return query.getSingleResult();
    }

    public List<Work> findByDeptName(String deptName){
        return em.createQuery("select w from Work w join Department d on w.department = d and d.deptName = :name")
                .setParameter("name",deptName)
                .getResultList();
    }

    public List<Work> findAll() {
        return em.createQuery("select w from Work w order by w.workEndDate desc")
                .getResultList();
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
