package team.okky.personnel_management.domain;

import java.time.LocalTime;

public enum AttendanceTime {
    ON_TIME(LocalTime.of(10, 0, 0)),
    OFF_TIME(LocalTime.of(17,0, 0));

    LocalTime localTime;

    AttendanceTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
