package artem.strelcov.corporativeapplication.exception_handling;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleEmailException(EmailExistsException e ){
        return "register_email_fail";
    }

    @ExceptionHandler
    public String handlePasswordException(IncorrectPasswordException e){
        return "register_password_error";
    }

}
