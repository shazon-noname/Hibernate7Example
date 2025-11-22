package Models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    LinkedPurchaseListKey id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    Course course;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    Student student;
}
