package Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "PurchaseList")
public class PurchaseList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;
    private Integer price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
