package team.okky.personnel_management.service;

import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;


public interface AttendanceService {

    public List<Attendance> autoCreateAttendance();
    public Attendance onWork(Employee employee);
    public Attendance offWork(Employee employee);
    public List<AttendanceDTO.ListAll> viewAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO viewAllForPage(int pageNo);
    public AttendanceDTO.Status viewStatus();
    public List<AttendanceDTO.ListAll> viewStatusDetail(AttendanceStatus status);
    public List<AttendanceDTO.ListAll> viewByDate(LocalDate date);
    public List<AttendanceDTO.ListAll> viewByName(Long id);
    public List<AttendanceDTO.ListAll> viewByDateAndName(LocalDate date, Long id);

}
