package team.okky.personnel_management.access;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class GeoLocationDTO {
    private String subdivisionName;
    private String cityName;
}
