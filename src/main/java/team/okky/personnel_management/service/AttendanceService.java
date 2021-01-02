package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.repository.AttendanceRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    /**
     * 전체 직원 근태 목록 보여주기
     * detail, 최근 날짜, 출근 시각 내림차순
     */
    public List<Attendance> viewAll(){
        return attendanceRepository.findAllOrderByDateAndTime();
    }

    /**
     * 해당 날짜 검색
     * detail, 해당 날짜에 출근하지 않은 사람 상태(휴가, 결석 등) 표시해야함
     */
    public List<Attendance> viewByDate(Date date){
        return attendanceRepository.findAllByDate(date);
    }

    /**
     * 이름 검색(동명이인)
     */
    public List<Attendance> viewByName(String name){
        return attendanceRepository.findAllByName(name);
    }

    /**
     * 해당 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     */
    public List<Attendance> viewByName(Long id){
        return attendanceRepository.findAllById(id);
    }

    /**
     * 해당 날짜 + 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     */
    public List<Attendance> viewByDateOrName(Date date, Long id){
        return attendanceRepository.findAllByDateAndId(date, id);
    }

}
