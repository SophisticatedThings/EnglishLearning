package artem.strelcov.corporativeapplication.service;

import artem.strelcov.corporativeapplication.DAO.ResponseRepository;
import artem.strelcov.corporativeapplication.model.FeedbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ResponseService {

    ResponseRepository responseRepository;
    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Transactional
    public void saveResponse(FeedbackResponse feedbackResponse){
        responseRepository.save(feedbackResponse);
    }
    @Transactional
    public List<FeedbackResponse> getResponses(){
        return responseRepository.findAll();
    }
}
