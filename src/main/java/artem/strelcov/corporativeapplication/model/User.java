package artem.strelcov.corporativeapplication.model;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 60)

    private String password;

    @Column(name = "first_name", nullable = false,  length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.VISITOR;
    @Column(name = "verification_code", length = 64)
    public String verificationCode;
    @Column(name = "enabled")
    public boolean enabled;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<FeedbackResponse> responses = new LinkedList<>();
    public String getFullName(){
        return getFirstName() + " " +  getLastName();
    }

}
