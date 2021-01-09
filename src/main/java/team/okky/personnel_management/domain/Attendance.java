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
    @Column(name = "att_id")
    private Long attID;

    private LocalDate attDate;
    private LocalTime attOnTime;
    private LocalTime attOffTime;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="emp_id")
    private Employee employee;


    public Attendance(LocalDate attDate, LocalTime attOnTime, LocalTime attOffTime, AttendanceStatus attStatus, Employee employee) {
        this.attDate = attDate;
        this.attOnTime = attOnTime;
        this.attOffTime = attOffTime;
        this.attStatus = attStatus;
        this.employee = employee;
    }

    public void setAttOnTime(LocalTime attOnTime) {
        this.attOnTime = attOnTime;
    }

    public void setAttOffTime(LocalTime attOffTime) {
        this.attOffTime = attOffTime;
    }

    public void setAttStatus(AttendanceStatus attStatus) {
        this.attStatus = attStatus;
    }
}
