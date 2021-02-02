package team.okky.personnel_management.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final EntityManager em;

    public Employee save(Employee employee){
        em.persist(employee);
        return employee;
    }

    public Employee findOne(Long id){
        return em.find(Employee.class, id);
    }

    public List<Employee> findAll(){
        return em.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    public Employee remove(Employee employee){
        em.remove(employee);
        return employee;
    }

    public List<Employee> findAll(PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e join fetch e.department order by e.empJoinDate desc", Employee.class)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotal(){
        return em.createQuery("select count(e) from Employee e", Long.class)
                .getSingleResult().intValue();
    }

    public List<Employee> findAllByEmpName(String empName){
        return em.createQuery("select e from Employee e where e.empName = :name", Employee.class)
                .setParameter("name", empName)
                .getResultList();
    }

    public List<Employee> findAllByEmpName(String name, PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e join fetch e.department where e.empName = :name", Employee.class)
                .setParameter("name", name)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByName(String name){
        return em.createQuery("select count(e) from Employee e where e.empName = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult().intValue();
    }

    public List<Employee> findAllByDeptName(String deptName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e join fetch e.department where e.department.deptName = :deptName", Employee.class)
                .setParameter("deptName", deptName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotalByDeptName(String deptName){
        return em.createQuery("select count(e) from Employee e where e.department.deptName = :deptName", Long.class)
                .setParameter("deptName", deptName)
                .getSingleResult().intValue();
    }

    public List<Employee> findAllByDeptId(Long deptId){
        return em.createQuery("select e from Employee e where e.department.deptId = :deptId", Employee.class)
                .setParameter("deptId", deptId)
                .setMaxResults(5)
                .getResultList();
    }

}
