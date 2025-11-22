package Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer salary;
    private Integer age;
}
