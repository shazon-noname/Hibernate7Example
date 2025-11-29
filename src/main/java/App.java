import Models.Course;
import Models.DTO.StudentCourseInfo;
import Models.DTO.TeachersInfo;
import Services.*;
import Util.TableDatabase;
import Util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {
        TableDatabase.initDatabase();

        try (EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

//            PurchaseListService.populateLinkedPurchaseList(entityManager);

            LinkedHashMap<String, Integer> allCourse = CourseService.findAllCourse(entityManager);
            LinkedHashMap<String, Integer> sortedAllCourse = CourseService.sortingAllCourseDesc(allCourse);
            sortedAllCourse.forEach((name, price) -> System.out.println(name + " - " + price));


            List<TeachersInfo> teachersInfo = TeacherService.getTeachersInfo(entityManager);
            teachersInfo.forEach(System.out::println);


            List<StudentCourseInfo> studentCourseInfos = StudentService.fetchDetailedStudentInfo( entityManager, "Java", 18, 1000, 5000);
            studentCourseInfos.forEach(System.out::println);


            List<Course> ux = CourseService.selectCourse(entityManager,"UX");
            ux.forEach(System.out::println);

            SubscriptionService.populateSubscriptionListWithJoin(entityManager);

            List<StudentCourseInfo> result =
                    SubscriptionService.findBySubscriptionDate(entityManager, LocalDate.of(2018, 1, 2));
            result.forEach(System.out::println);

            StudentService.getTotalStudents(entityManager);

            TeacherService.getMaxSalaryOfTeachers(entityManager);
            TeacherService.getAvgSalaryOfTeachers(entityManager);
            TeacherService.getSumSalaryOfTeachers(entityManager);

            transaction.commit();
        }
    }
}
