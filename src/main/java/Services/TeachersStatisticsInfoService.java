package Services;

import Models.DTO.TeachersStatisticsInfo;
import Models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class TeachersStatisticsInfoService {
    //aggregateQuery
    public static void getTeachersSalaryStatistics(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeachersStatisticsInfo> query = criteriaBuilder.createQuery(TeachersStatisticsInfo.class);
        Root<Teacher> root = query.from(Teacher.class);

        Expression<Integer> maxSalaryOfTeachers = criteriaBuilder.max(root.get("salary"));
        Expression<Double> avgSalaryOfTeachers = criteriaBuilder.avg(root.get("salary"));
        Expression<Long> sumSalaryOfTeachers = criteriaBuilder.sum(root.get("salary").as(Long.class));
        Expression<Long> totalDistinctNoOfEmployees = criteriaBuilder.countDistinct(root.get("salary"));


        query.select(criteriaBuilder.construct(
                TeachersStatisticsInfo.class,
                maxSalaryOfTeachers,
                avgSalaryOfTeachers,
                sumSalaryOfTeachers,
                totalDistinctNoOfEmployees
                ));

        TeachersStatisticsInfo singleResult = entityManager.createQuery(query).getSingleResult();
        System.out.println(singleResult);
    }
}
