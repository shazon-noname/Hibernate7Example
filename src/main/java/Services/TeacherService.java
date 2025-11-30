package Services;

import Models.DTO.TeachersInfo;
import Models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;

import java.util.List;

public class TeacherService {
    public static List<TeachersInfo> getTeachersInfo(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeachersInfo> query = criteriaBuilder.createQuery(TeachersInfo.class);
        Root<Teacher> root = query.from(Teacher.class);

        Path<Object> agePath = root.get("age");
        Path<Object> namePath = root.get("name");
        Path<Object> salaryPath = root.get("salary");

        query.select(
                criteriaBuilder.construct(
                        TeachersInfo.class,
                        agePath,
                        namePath,
                        salaryPath
                )).groupBy(agePath, namePath, salaryPath);

        return entityManager.createQuery(query).getResultList();
    }

    public static void getMaxSalaryOfTeachers(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        Root<Teacher> from = query.from(Teacher.class);

        // -- subquery max salary

        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<Teacher> subQueryRoot = subquery.from(Teacher.class);
        Subquery<Integer> salary = subquery.select(criteriaBuilder.max(subQueryRoot.get("salary")));

        // -- main select

        query.select(criteriaBuilder.tuple(
                from.get("name").alias("name"),
                from.get("salary").alias("salary")
        )).where(criteriaBuilder.equal(from.get("salary"), salary));

        List<Tuple> resultList = entityManager.createQuery(query).getResultList();

        resultList.forEach(t ->
                System.out.println("Name: " + t.get("name") + " = Salary: " + t.get("salary"))
        );
    }

    public static void getAvgSalaryOfTeachers(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Teacher> root = query.from(Teacher.class);

        query.select(criteriaBuilder.avg(root.get("salary")));

        Double singleResult = entityManager.createQuery(query).getSingleResult();

        System.out.println("Avg salary : " + singleResult);

    }

    public static void getSumSalaryOfTeachers(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Teacher> root = query.from(Teacher.class);

        query.select(criteriaBuilder.sum(root.get("salary").as(Long.class)));

        Long singleResult = entityManager.createQuery(query).getSingleResult();

        System.out.println("Sum salary : " + singleResult);
    }

    public static void getTotalDistinctNoOfEmployees(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Teacher> studentRoot = query.from(Teacher.class);

        query.select(criteriaBuilder.countDistinct(studentRoot)); // countDistinct() return unique element

        Long singleResult = entityManager.createQuery(query).getSingleResult();
        System.out.println("Total number of unique teachers : " + singleResult);
    }
}
