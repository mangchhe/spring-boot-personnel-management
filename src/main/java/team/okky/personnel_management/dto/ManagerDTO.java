package team.okky.personnel_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.SecondaryTable;
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
