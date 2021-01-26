package team.okky.personnel_management.manager;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ManagerDTO {

    @Builder
    @Getter @Setter
    public static class profile{
        private String mnEmail;
        private LocalDateTime currentAccessDate;
        private String accessArea;
        private String mnPw;
    }
}
