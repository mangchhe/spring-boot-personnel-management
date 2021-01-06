package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AttendanceDTO {

    @Builder
    @Getter
    public static class Status {
        private int onCnt;
        private int offCnt;
        private int absenceCnt;
        private int lateCnt;
        private int vacationCnt;
        private int sickCnt;
    }
}
