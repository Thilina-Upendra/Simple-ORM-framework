package lk.ijse.dep8.orm.entity;

import lk.ijse.dep8.orm.annotation.Entity;
import lk.ijse.dep8.orm.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(value = "New_Student")
@Data @NoArgsConstructor @AllArgsConstructor
public class Student implements Serializable {
@Id
    private String id;
    private String name;
    private String address;
}
