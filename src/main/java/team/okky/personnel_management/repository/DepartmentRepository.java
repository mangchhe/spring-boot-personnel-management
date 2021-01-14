package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Department;

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
}
