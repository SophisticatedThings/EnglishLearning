package artem.strelcov.corporativeapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping ("/login")
    public String getLoginPage(){
        return "login";
    }
    @GetMapping("/login_success")
    public String getSuccessPage(){
        return "login_success";
    }

}
