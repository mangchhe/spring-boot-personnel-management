package team.okky.personnel_management.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id @GeneratedValue
    @Column(name = "dept_id")
    private Long deptId;
    private String deptName;
    private String deptChargeName;
    private String deptAddress;

    public Department(String deptName, String deptChargeName, String deptAddress) {
        this.deptName = deptName;
        this.deptChargeName = deptChargeName;
        this.deptAddress = deptAddress;
    }
}
