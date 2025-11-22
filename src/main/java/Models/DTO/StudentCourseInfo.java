package Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StudentCourseInfo {
    private String studentName;
    private Integer studentAge;
    private String courseName;
    private Integer coursePrice;
    private String teacherName;
    private Integer teacherSalary;
    private LocalDate subscriptionDate;
}
