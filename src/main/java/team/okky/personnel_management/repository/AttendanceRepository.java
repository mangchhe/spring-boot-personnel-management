package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.AttendanceStatus;
import team.okky.personnel_management.domain.Employee;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {

    private final EntityManager em;

    public Attendance save(Attendance attendance){
        em.persist(attendance);
        return attendance;
    }

    public Attendance findOne(Long id){
        return em.find(Attendance.class, id);
    }

    public List<Attendance> findAll(){
        return em.createQuery("select a from Attendance a", Attendance.class)
                .getResultList();
    }

    public Attendance remove(Attendance attendance){
        em.remove(attendance);
        return attendance;
    }

    public List<Attendance> findAllOrderByDateAndTime(){
        return em.createQuery("select a from Attendance a order by a.att_date desc, a.att_on_time desc", Attendance.class)
                .getResultList();
    }

    public List<Attendance> findAllByDate(LocalDate date){
        return em.createQuery("select a from Attendance a where a.att_date = :att_date", Attendance.class)
                .setParameter("att_date", date)
                .getResultList();
    }

    public List<Attendance> findAllByName(String name){
        return em.createQuery("select a from Attendance a where a.employee.emp_name = :emp_name", Attendance.class)
                .setParameter("emp_name", name)
                .getResultList();
    }

    public List<Attendance> findAllById(Long id){
        return em.createQuery("select a from Attendance a where a.employee.emp_id = :emp_id", Attendance.class)
                .setParameter("emp_id", id)
                .getResultList();
    }

    public List<Attendance> findAllByDateAndId(LocalDate date, Long id){
        return em.createQuery("select a from Attendance a where a.employee.emp_id = :emp_id and a.att_date = :att_date", Attendance.class)
                .setParameter("emp_id", id)
                .setParameter("att_date", date)
                .getResultList();
    }

    public List<Employee> findAllByOn(){
        return em.createQuery("select a.employee from Attendance a where a.att_date = :att_date", Employee.class)
                .setParameter("att_date", LocalDate.now())
                .getResultList();
    }

    public List<Attendance> findAllByEmployeeAndDate(Employee employee, LocalDate date){
        return em.createQuery("select a from Attendance a where a.employee in :employee and a.att_date = :att_date", Attendance.class)
                .setParameter("employee", employee)
                .setParameter("att_date", date)
                .getResultList();
    }


    public List<Employee> findAllByStatus(AttendanceStatus status){
        return em.createQuery("select a.employee from Attendance a where a.att_status = :status", Employee.class)
                .setParameter("status", status)
                .getResultList();
    }

}
