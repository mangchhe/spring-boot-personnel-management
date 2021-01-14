package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.PageRequestDTO;

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

    public List<Employee> findAllOrderByJoinDate(PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e order by e.empJoinDate desc", Employee.class)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findAllOrderByJoinDateTotal(){
        return em.createQuery("select count(e.empId) from Employee e", Long.class)
                .getSingleResult().intValue();
    }

    public List<Employee> findAllByName(String name, PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e where e.empName = :name", Employee.class)
                .setParameter("name", name)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findAllByNameTotal(String name){
        return em.createQuery("select count(e.empId) from Employee e where e.empName = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult().intValue();
    }

    public List<Employee> findAllByDept(String deptName, PageRequestDTO pageRequestDTO){
        return em.createQuery("select e from Employee e where e.department.deptName = :deptName", Employee.class)
                .setParameter("deptName", deptName)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findAllByDeptTotal(String deptName){
        return em.createQuery("select count(e.empId) from Employee e where e.department.deptName = :deptName", Long.class)
                .setParameter("deptName", deptName)
                .getSingleResult().intValue();
    }

}
