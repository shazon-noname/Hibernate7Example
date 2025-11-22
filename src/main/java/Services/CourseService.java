package Services;

import Models.Course;
import Util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseService {

    public static LinkedHashMap<String, Integer> findAllCourse() {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);

        Root<Course> courseRoot = query.from(Course.class);

        query.select(criteriaBuilder.tuple(
                        courseRoot.get("name").alias("name"),
                        courseRoot.get("price").alias("price")
                )).where(criteriaBuilder.greaterThan(courseRoot.get("price"), 10_000))
                .orderBy(criteriaBuilder.desc(courseRoot.get("price")));

        List<Tuple> allCourse = entityManager.createQuery(query).getResultList();

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Tuple t : allCourse) {
            String name = t.get("name", String.class);
            Integer price = t.get("price", Integer.class);
            result.put(name, price);
        }

        entityManager.close();
        return result;
    }

    public static LinkedHashMap<String, Integer> sortingAllCourseDesc(LinkedHashMap<String, Integer> allCourse) {
        return allCourse.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new
                ));
    }

    public static List<Course> selectCourse(String name) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class, "courseName");

        courseCriteriaQuery.where(criteriaBuilder.like(courseRoot.get("name"),parameter));
        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery);
        query.setParameter("courseName", "%" + name + "%");

        List<Course> resultList = query.getResultList();
        entityManager.close();

        return resultList;
    }
}
