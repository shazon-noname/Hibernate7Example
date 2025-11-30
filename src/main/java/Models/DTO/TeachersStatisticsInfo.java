package Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TeachersStatisticsInfo {
    private Integer maxSalaryOfTeachers;
    private Double avgSalaryOfTeachers;
    private Long sumSalaryOfTeachers;
    private Long totalDistinctNoOfEmployees;

}
