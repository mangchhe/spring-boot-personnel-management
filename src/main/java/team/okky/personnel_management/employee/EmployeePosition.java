package team.okky.personnel_management.employee;

import java.util.Arrays;

public enum EmployeePosition {

    EXECUTIVES("임원"),
    GENERAL_MANAGER("부장"),
    DEPUTY_GENERAL_MANAGER("차장"),
    MANAGER("과장"),
    ASSISTANT_MANAGER("대리"),
    SENIOR_STAFF("주임"),
    STAFF("사원"),
    INTERN("인턴"),
    DEFAULT("미정");

    String position;

    EmployeePosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public static EmployeePosition findByEmployeePosition(String position){
        return Arrays.stream(EmployeePosition.values())
                .filter(p -> p.getPosition().equals(position))
                .findAny()
                .orElse(DEFAULT);
    }
}
