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
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.ZoneId;
import java.util.List;

public class StudentService {
    public static List<StudentCourseInfo> fetchDetailedStudentInfo(EntityManager entityManager, String courseName, int age, int price, int salary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);

        Root<Student> studentRoot = query.from(Student.class);
        Root<Course> courseRoot = query.from(Course.class);
        Root<Teacher> teacherRoot = query.from(Teacher.class);
        Root<Subscription> subscriptionRoot = query.from(Subscription.class);

        query.select(criteriaBuilder.tuple(
                studentRoot.alias("student"),
                courseRoot.alias("course"),
                teacherRoot.alias("teacher"),
                subscriptionRoot.alias("subscription")
        ));

        Predicate studentRestriction = criteriaBuilder.and(
                criteriaBuilder.greaterThan(studentRoot.get("age"), age),
                criteriaBuilder.isNotNull(studentRoot.get("registrationDate"))
        );

        Predicate courseRestriction = criteriaBuilder.and(
                criteriaBuilder.like(courseRoot.get("name"), "%" + courseName + "%"),
                criteriaBuilder.greaterThan(courseRoot.get("price"), price)
        );

        Predicate teacherRestriction = criteriaBuilder.equal(teacherRoot.get("salary"), salary);

        Predicate subscriptionRestriction = criteriaBuilder.and(
                criteriaBuilder.equal(subscriptionRoot.get("student"), studentRoot),
                criteriaBuilder.equal(subscriptionRoot.get("course"), courseRoot)
        );

        query.where(criteriaBuilder.and(
                studentRestriction,
                courseRestriction,
                teacherRestriction,
                subscriptionRestriction
        ));

        List<Tuple> resultList = entityManager.createQuery(query).getResultList();

        return resultList.stream()
                .map(tuple ->
                {
                    Student student = tuple.get("student", Student.class);
                    Course course = tuple.get("course", Course.class);
                    Teacher teacher = tuple.get("teacher", Teacher.class);
                    Subscription subscription = tuple.get("subscription", Subscription.class);

                    return new StudentCourseInfo(
                            student.getName(),
                            student.getAge(),
                            course.getName(),
                            course.getPrice(),
                            teacher.getName(),
                            teacher.getSalary(),
                            subscription.getSubscriptionDate()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                    );
                }).toList();
    }

    public static void getTotalStudents(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Student> studentRoot = query.from(Student.class);

        query.select(criteriaBuilder.count(studentRoot));

        Long singleResult = entityManager.createQuery(query).getSingleResult();
        System.out.println("Total number of students : " + singleResult);
    }
}

