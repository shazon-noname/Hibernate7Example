import Models.Course;
import Models.DTO.StudentCourseInfo;
import Models.DTO.TeachersInfo;
import Services.CourseService;
import Services.PurchaseListService;
import Services.StudentService;
import Services.TeacherService;
import Util.TableDatabase;
import Util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.LinkedHashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {
        TableDatabase.initDatabase();

        try (EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            LinkedHashMap<String, Integer> allCourse = CourseService.findAllCourse();
            LinkedHashMap<String, Integer> sortedAllCourse = CourseService.sortingAllCourseDesc(allCourse);
            sortedAllCourse.forEach((name, price) -> System.out.println(name + " - " + price));


            List<TeachersInfo> teachersInfo = TeacherService.getTeachersInfo(entityManager);
            teachersInfo.forEach(System.out::println);


            List<StudentCourseInfo> studentCourseInfos = StudentService.fetchDetailedStudentInfo( "Java", 18, 1000, 5000);
            studentCourseInfos.forEach(System.out::println);


            List<Course> ux = CourseService.selectCourse("UX");
            ux.forEach(System.out::println);

            PurchaseListService.populateLinkedPurchaseListWithJoin();

            transaction.commit();
        }
    }
}
