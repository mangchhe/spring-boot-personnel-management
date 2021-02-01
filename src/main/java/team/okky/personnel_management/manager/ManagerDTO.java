package team.okky.personnel_management.manager;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team.okky.personnel_management.access.GeoLocationDTO;
import team.okky.personnel_management.access.GeoService;

import java.time.LocalDateTime;

public class ManagerDTO {

    @Builder
    @Getter @Setter
    public static class Profile{
        private String mnEmail;
        private LocalDateTime currentAccessDate;
        private String accessArea;
    }

    @Builder
    @Getter @Setter
    public static class AccessRecord{
        private LocalDateTime accessDate;
        private String accessArea;
    }

    @Builder
    @Getter
    public static class AccessRecordWithGeo{
        private AccessRecord accessRecord;
        private GeoLocationDTO geoLocation;
    }

    @Builder
    @Getter @Setter
    public static class UpdatePw{
        private String curPw;
        private String newPw;
        private String newPwCheck;
    }
}
