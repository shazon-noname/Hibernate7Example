package Services;

import Models.DTO.TeachersInfo;
import Models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

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
                )).groupBy(agePath,namePath, salaryPath);

        return entityManager.createQuery(query).getResultList();
    }
}
