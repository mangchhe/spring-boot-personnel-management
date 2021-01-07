package team.okky.personnel_management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Department {

    @Id @GeneratedValue
    private Long dept_id;
    private String dept_name;
    private String dept_charge_name;
    private String dept_address;

    public Department(String dept_name, String dept_charge_name, String dept_address) {
        this.dept_name = dept_name;
        this.dept_charge_name = dept_charge_name;
        this.dept_address = dept_address;
    }

    public Department() {

    }
}
