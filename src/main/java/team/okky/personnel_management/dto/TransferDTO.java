package team.okky.personnel_management.dto;

import java.time.LocalDate;

public class TransferDTO {

    public static class TransferResultDTO {
        String employeeId;
        String employeeName;
        String departmentName;
        String transferDepartmentName;
        String position;
        String transferPosition;
        String transferDate;
    }

    public static class TransferUpdateFormDTO {
        String employeeId;
        String departmentName;
        String transferPosition;
        LocalDate transferDate;
    }

}
