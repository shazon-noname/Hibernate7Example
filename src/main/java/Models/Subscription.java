package Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Subscriptions")
public class Subscription {
    @EmbeddedId
    SubscriptionKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student studentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course courseId;
    @Column(name = "subscription_date")
    Date subscriptionDate;
}
