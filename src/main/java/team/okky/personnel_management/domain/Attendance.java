package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id @GeneratedValue
    private Long att_id;

    private LocalDate att_date;
    private LocalTime att_on_time;
    private LocalTime att_off_time;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus att_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public Attendance(LocalDate att_date, LocalTime att_on_time, LocalTime att_off_time, AttendanceStatus att_status, Employee employee) {
        this.att_date = att_date;
        this.att_on_time = att_on_time;
        this.att_off_time = att_off_time;
        this.att_status = att_status;
        this.employee = employee;
    }

    public void setAtt_status(AttendanceStatus att_status) {
        this.att_status = att_status;
    }

}
