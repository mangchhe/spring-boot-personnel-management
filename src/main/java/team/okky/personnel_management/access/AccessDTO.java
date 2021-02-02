package team.okky.personnel_management.access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AccessDTO {
    @Builder
    @Getter @Setter
    public static class AccessRecord{
        private LocalDateTime accessDate;
        private String accessArea;
    }

    @Builder
    @Getter @Setter
    public static class AccessRecordWithGeo {
        private AccessRecord accessRecord;
        private GeoLocation geoLocation;
    }

    @Builder
    @Getter @Setter
    public static class GeoLocation {
        private String subdivisionName;
        private String cityName;
    }
}
