package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;

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

    public List<Employee> findAllOrderByJoinDate(){
        return em.createQuery("select e from Employee e order by e.empJoinDate desc", Employee.class)
                .getResultList();
    }

    public List<Employee> findAllByName(String name){
        return em.createQuery("select e from Employee e where e.empName = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Employee> findAllByDept(String deptName){
        return em.createQuery("select e from Employee e where e.department.deptName = :deptName", Employee.class)
                .setParameter("deptName", deptName)
                .getResultList();
    }

}
