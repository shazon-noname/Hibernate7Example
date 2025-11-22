package Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@ToString
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private TypeCourse type;
    private String description;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @Column(name = "students_count")
    private Integer studentsCount;
    private Integer price;
    @Column(name = "price_per_hour")
    private Integer pricePerHour;
}
