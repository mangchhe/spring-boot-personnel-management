package team.okky.personnel_management.attendance;

import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;


public interface AttendanceService {

    public List<Attendance> autoCreateAttendance();
    public Attendance onWork(Employee employee);
    public Attendance offWork(Employee employee);
    public List<AttendanceDTO.Index> findAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO findPage(int pageNo);
    public AttendanceDTO.Status findAllOnlyStatus();
    public List<AttendanceDTO.Index> findAllByStatus(AttendanceStatus status);
    public List<AttendanceDTO.Index> findAllByDate(LocalDate date);
    public List<AttendanceDTO.Index> findAllByName(Long id);
    public List<AttendanceDTO.Index> findAllByDateAndName(LocalDate date, Long id);

}
