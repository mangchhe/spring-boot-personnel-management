package team.okky.personnel_management.transfer;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import team.okky.personnel_management.employee.EmployeeDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;

public class TransferDTO {

    @Builder
    @Getter
    public static class Info {
        String employeeName;
        String curDepartmentName;
        String transferDepartmentName;
        String curPosition;
        String transferPosition;
        LocalDate approveDate;
        LocalDate appointDate;
    }

    @Builder
    @Getter
    public static class InfoWithPage {
        List<Info> transInfo;
        PageResultDTO pageResult;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class AddForm {
        Long employeeId;
        String departmentName;
        String transferPosition;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate transferDate;
    }

}
