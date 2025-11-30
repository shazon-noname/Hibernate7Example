package Services;

import Models.Course;
import Models.Student;
import Models.Subscription;
import Models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CriteriaJoinService {

    public static void getInfoStudents(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);

        Root<Subscription> subscriptionRoot = query.from(Subscription.class);

        Join<Subscription, Student> studentJoin = subscriptionRoot.join("student");
        Join<Subscription, Course> courseJon = subscriptionRoot.join("course");
        Join<Course, Teacher> teacherJoin = courseJon.join("teacher");

        query.select(
                criteriaBuilder.tuple(
                        subscriptionRoot.alias("subscription"),
                        studentJoin.alias("student"),
                        courseJon.alias("course"),
                        teacherJoin.alias("teacher")
                )
        );

        List<Tuple> resultList = entityManager.createQuery(query).getResultList();

        for (Tuple t : resultList) {
            Subscription subscription = t.get("subscription", Subscription.class);
            Student student = t.get("student", Student.class);
            Course course = t.get("course", Course.class);
            Teacher teacher = t.get("teacher", Teacher.class);


            System.out.println(
                    student.getName() +
                            " -> " + course.getName() +
                            " -> " + teacher.getName() +
                            " -> " + subscription.getSubscriptionDate()
            );
        }
    }


}
