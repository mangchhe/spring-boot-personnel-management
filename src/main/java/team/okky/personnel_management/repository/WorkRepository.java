package team.okky.personnel_management.repository;

import org.springframework.stereotype.Repository;
import team.okky.personnel_management.controller.WorkSearch;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.WorkFindDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<WorkFindDto> findByWorkName(String workName){
        return em.createQuery("select new team.okky.personnel_management.dto.WorkFindDto(w)" +
                "from Work w where w.work_name = :name")
                .setParameter("name",workName)
                .getResultList();
    }

    public List<WorkFindDto> findByEmpName(String empName){
        return em.createQuery("select new team.okky.personnel_management.dto.WorkFindDto(w) " +
                "from Work w join Employee e on w = e.work and e.emp_name = :name")
                .setParameter("name",empName)
                .getResultList();
    }

    public List<WorkFindDto> findByDeptName(String deptName){
        return em.createQuery("select new team.okky.personnel_management.dto.WorkFindDto(w) " +
                "from Work w join Department d on w.department = d and d.dept_name = :name")
                .setParameter("name",deptName)
                .getResultList();
    }

    public List<WorkFindDto> findAll() {
        return em.createQuery("select new team.okky.personnel_management.dto.WorkFindDto(w) " +
                "from Work w order by w.work_end_date desc")
                .getResultList();
    }

    public List<WorkFindDto> filteringList(WorkSearch workSearch) {
        String nameType = workSearch.getNameType();
        String name = workSearch.getName();
        if(nameType.equals("workName") && !name.isEmpty()) {
            return findByWorkName(name);
        }
        else if(nameType.equals("deptName")&& !name.isEmpty()) {
            return findByDeptName(name);
        }
        else if(nameType.equals("empName")&& !name.isEmpty()){
            return findByEmpName(name);
        }
        else return findAll();

    }

    public Work remove(Work work){
        em.remove(work);
        return work;
    }
}
