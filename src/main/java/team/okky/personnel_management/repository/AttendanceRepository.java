package team.okky.personnel_management.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.okky.personnel_management.domain.Attendance;

import javax.persistence.EntityManager;
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
        return em.createQuery("select a from Attendance a")
                .getResultList();
    }

    public Attendance remove(Attendance attendance){
        em.remove(attendance);
        return attendance;
    }
}
