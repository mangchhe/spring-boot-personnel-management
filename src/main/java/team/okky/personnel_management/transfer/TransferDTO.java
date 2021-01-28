package team.okky.personnel_management.transfer;

import lombok.Builder;

import java.time.LocalDate;

public class TransferDTO {

    @Builder
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
    public static class UpdateForm {
        String employeeId;
        String departmentName;
        String transferPosition;
        LocalDate transferDate;
    }

}
