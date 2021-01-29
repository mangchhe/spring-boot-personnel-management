package team.okky.personnel_management.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

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

    public Attendance remove(Attendance attendance){
        em.remove(attendance);
        return attendance;
    }

    public List<Attendance> findAll(PageRequestDTO pageRequestDTO){
        return em.createQuery("select a from Attendance a order by a.attDate desc, a.attOnTime desc", Attendance.class)
                .setFirstResult(pageRequestDTO.getPage())
                .setMaxResults(pageRequestDTO.getSize())
                .getResultList();
    }

    public int findTotal(){
        return em.createQuery("select count(a.attID) from Attendance a", Long.class)
                .getSingleResult().intValue();
    }

    public List<Attendance> findAllByDate(LocalDate date){
        return em.createQuery("select a from Attendance a where a.attDate = :att_date", Attendance.class)
                .setParameter("att_date", date)
                .getResultList();
    }

    public List<Attendance> findAllById(Long id){
        return em.createQuery("select a from Attendance a where a.employee.empId = :emp_id", Attendance.class)
                .setParameter("emp_id", id)
                .getResultList();
    }

    public List<Attendance> findAllByDateAndId(LocalDate date, Long id){
        return em.createQuery("select a from Attendance a where a.employee.empId = :emp_id and a.attDate = :att_date", Attendance.class)
                .setParameter("emp_id", id)
                .setParameter("att_date", date)
                .getResultList();
    }

    public List<Attendance> findAllByEmployeeAndDate(Employee employee, LocalDate date){
        return em.createQuery("select a from Attendance a where a.employee in :employee and a.attDate = :att_date", Attendance.class)
                .setParameter("employee", employee)
                .setParameter("att_date", date)
                .getResultList();
    }

    public List<Attendance> findAllByStatus(AttendanceStatus status){
        return em.createQuery("select a from Attendance a where a.attStatus = :status", Attendance.class)
                .setParameter("status", status)
                .getResultList();
    }

}
