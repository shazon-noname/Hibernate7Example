package Services;

import Models.Course;
import Models.DTO.StudentCourseInfo;
import Models.Student;
import Models.Subscription;
import Models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SubscriptionService {
    public static void populateSubscriptionListWithJoin(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> query = criteriaBuilder.createQuery(Subscription.class);
        Root<Subscription> rootSubscription = query.from(Subscription.class);

        rootSubscription.join("student");
        rootSubscription.join("course");

        query.where(criteriaBuilder.gt(rootSubscription.get("course").get("price"), 1000));

        List<Subscription> resultList = entityManager.createQuery(query).getResultList();

        for (Subscription s : resultList) {
            System.out.println(
                    s.getStudent().getName() + " -> " + s.getCourse().getName()
            );
        }
    }

    public static List<StudentCourseInfo> findBySubscriptionDate(EntityManager entityManager, LocalDate localDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        Root<Subscription> root = query.from(Subscription.class);

        Join<Object, Object> courseJoin = root.join("course");
        Join<Object, Object> studentJoin = root.join("student");
        Join<Object, Object> teacherJoin = courseJoin.join("teacher");

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.select(criteriaBuilder.tuple(
            studentJoin.alias("student"),
            courseJoin.alias("course"),
            teacherJoin.alias("teacher"),
            root.alias("subscription")
        ));

        List<Tuple> resultList = entityManager.createQuery(query).getResultList();

        query.where(criteriaBuilder.equal(root.get("subscriptionDate"),date));

        return resultList.stream()
                .map(t -> new StudentCourseInfo(
                        t.get("student", Student.class).getName(),
                        t.get("student", Student.class).getAge(),
                        t.get("course", Course.class).getName(),
                        t.get("course", Course.class).getPrice(),
                        t.get("teacher", Teacher.class).getName(),
                        t.get("teacher", Teacher.class).getSalary(),
                        t.get("subscription", Subscription.class).getSubscriptionDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )).toList();
    }
}
