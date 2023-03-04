package artem.strelcov.corporativeapplication.DAO;

import artem.strelcov.corporativeapplication.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<FeedbackResponse, Long> {
}
