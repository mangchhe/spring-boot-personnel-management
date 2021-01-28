package team.okky.personnel_management.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final EntityManager em;

    public Department save(Department department){
        em.persist(department);
        return department;
    }

    public Department findOne(Long id){
        return em.find(Department.class, id);
    }

    public List<Department> findAll(){
        return em.createQuery("select a from Department a", Department.class)
                .getResultList();
    }

    public Department remove(Department department){
        em.remove(department);
        return department;
    }

    public Department findByName(String name){
        return em.createQuery("select d from Department d where d.deptName = :name", Department.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
