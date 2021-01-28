package team.okky.personnel_management.transfer;

import java.time.LocalDate;

public class TransferDTO {

    public static class Info {
        String employeeName;
        String curDepartmentName;
        String transferDepartmentName;
        String curPosition;
        String transferPosition;
        LocalDate approveDate;
        LocalDate appointDate;
    }

    public static class updateForm {
        String employeeId;
        String departmentName;
        String transferPosition;
        LocalDate transferDate;
    }

}
