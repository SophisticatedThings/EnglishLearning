package artem.strelcov.corporativeapplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping ("/login")
    public String getLoginPage(){
        return "login";
    }
    @GetMapping("/doLogin")
    @PreAuthorize("hasAuthority('index:read')")
    public String doLogin(){
        return "users";
    }
     @GetMapping("/login_success")
    public String getSuccessPage(){
        return "login_success";
    }
    /*
    @RequestMapping (value = "/login_success_handler", method = RequestMethod.POST)
    public String loginSuccessHandler(){
        return "index";
    }
    @GetMapping("/logout")
    public String loginError(){
        return "login_error";
    }  */

}
