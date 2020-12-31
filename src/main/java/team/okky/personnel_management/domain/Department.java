package team.okky.personnel_management.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Department {

    @Id @GeneratedValue
    Long dept_id;
    String dept_name;
    String dept_chrage_name;
    String dept_address;
}
