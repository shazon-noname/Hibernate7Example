package Services;

import Models.Subscription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class SubscriptionService {
    public static void populateSubscriptionListWithJoin(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> query = criteriaBuilder.createQuery(Subscription.class);
        Root<Subscription> rootSubscription = query.from(Subscription.class);

        rootSubscription.join("studentId");
        rootSubscription.join("courseId");

        query.where(criteriaBuilder.gt(rootSubscription.get("courseId").get("price"), 1000));

        List<Subscription> resultList = entityManager.createQuery(query).getResultList();

        for (Subscription s : resultList) {
            System.out.println(
                    s.getStudentId().getName() + " -> " +  s.getCourseId().getName()
            );
        }
    }
}
