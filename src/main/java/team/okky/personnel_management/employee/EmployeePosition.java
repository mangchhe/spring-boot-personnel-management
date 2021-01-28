package team.okky.personnel_management.employee;

public enum EmployeePosition {

    EXECUTIVES("임원"),
    GENERAL_MANAGER("부장"),
    DEPUTY_GENERAL_MANAGER("차장"),
    MANAGER("과장"),
    ASSISTANT_MANAGER("대리"),
    SENIOR_STAFF("주임"),
    STAFF("사원");

    String position;

    EmployeePosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
