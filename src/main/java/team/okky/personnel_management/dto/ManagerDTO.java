package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ManagerDTO {

    @Builder
    @Getter @Setter
    public static class profile{
        private String mnEmail;
        private LocalDate currentAccessDate;
        private String accessArea;
        private String mnPw;
    }
}
