package artem.strelcov.corporativeapplication.DAO;

import artem.strelcov.corporativeapplication.model.Role;
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
    @Query("select u.role from User u where u.email=:email")
    public Role findRole(String email);
    @Modifying
    @Query("update User set enabled=false where email=:email ")
    public void blockUser(String email);
    @Modifying
    @Query("update User set enabled=true where email=:email ")
    public void unblockUser(String email);

}
