package team.okky.personnel_management.attendance;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceDTO {

    @Builder
    @Getter @Setter
    @EqualsAndHashCode
    public static class Status {
        private int onCnt;
        private int offCnt;
        private int absenceCnt;
        private int lateCnt;
        private int vacationCnt;
        private int sickCnt;
    }

    @Getter @Setter
    public static class StatusAndList {
        private AttendanceDTO.Status status;
        private List<ListAll> attendanceList;
        private PageResultDTO pageResultDTO;
    }

    @Builder
    @Getter @Setter
    public static class ListAll {
        private LocalDate attDate;
        private LocalTime attOnTime;
        private LocalTime attOffTime;
        private AttendanceStatus attStatus;
        private String empName;
        private String empPosition;
        private String deptName;
        private String dayOfWeek;
    }
}
