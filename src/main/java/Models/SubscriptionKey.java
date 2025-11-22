package Models;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubscriptionKey implements Serializable {
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "course_id")
    private Integer courseId;
}
