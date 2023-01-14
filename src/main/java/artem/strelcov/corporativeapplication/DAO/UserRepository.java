package artem.strelcov.corporativeapplication.DAO;

import artem.strelcov.corporativeapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    @Query("select u from User u where u.verificationCode=?1")
    public User findByVerificationCode(String code);

}
