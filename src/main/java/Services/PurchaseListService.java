package Services;

import Models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PurchaseListService {
    public static void populateLinkedPurchaseList(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> purchaseListCriteriaQuery = criteriaBuilder.createQuery(PurchaseList.class);
        Root<PurchaseList> purchaseListRoot = purchaseListCriteriaQuery.from(PurchaseList.class);

        purchaseListCriteriaQuery.select(purchaseListRoot);

        List<PurchaseList> resultList = entityManager.createQuery(purchaseListCriteriaQuery).getResultList();

        for (PurchaseList purchase : resultList) {

            CriteriaQuery<Integer> studentCriteria = criteriaBuilder.createQuery(Integer.class);
            Root<Student> studentRoot = studentCriteria.from(Student.class);
            studentCriteria.select(studentRoot.get("id"))
                    .where(criteriaBuilder.equal(studentRoot.get("name"), purchase.getStudentName()));
            Integer studentId = entityManager.createQuery(studentCriteria).getSingleResult();

            CriteriaQuery<Integer> courseCriteria = criteriaBuilder.createQuery(Integer.class);
            Root<Course> courseRoot = courseCriteria.from(Course.class);
            courseCriteria.select(courseRoot.get("id"))
                    .where(criteriaBuilder.equal(courseRoot.get("name"), purchase.getCourseName()));
            Integer courseId = entityManager.createQuery(courseCriteria).getSingleResult();

            LinkedPurchaseListKey linkedPurchaseListKey = new LinkedPurchaseListKey();
            linkedPurchaseListKey.setCourseId(courseId);
            linkedPurchaseListKey.setStudentId(studentId);

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(linkedPurchaseListKey);

            entityManager.persist(linkedPurchaseList);
        }
    }
}
