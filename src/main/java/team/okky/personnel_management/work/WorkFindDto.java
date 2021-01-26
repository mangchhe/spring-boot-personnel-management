package team.okky.personnel_management.work;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkFindDto {

    private Long workId;
    private String workName;
    private String workChargeName;
    private LocalDate workStartDate;
    private LocalDate workEndDate;
    private String deptName;
    private String employees;
    private Boolean workStatus;

}
