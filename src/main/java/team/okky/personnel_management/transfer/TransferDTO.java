package team.okky.personnel_management.transfer;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import team.okky.personnel_management.employee.EmployeeDTO;

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
