package artem.strelcov.corporativeapplication.controller;


import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1")
public class AppController {
    UserRepository userRepository;
    @Autowired
    public AppController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping (value = "/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String  encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "register_success";


    }
    @PostMapping()
    public String save(@RequestBody User user){
        if(isUserValid(user)){
            userRepository.save(user);
        }
        else
            return "Loh";
        return "register_success";
    }
    @GetMapping(value = "/success.html")
    public String showSuccessPage(){
        return "register_success";
    }
    private boolean isUserValid(User user) {
        return true;
    }
    @GetMapping(value = "/login")
    public String log(Model model){
        List<User> list = userRepository.findAll();
        model.addAttribute("listUsers", list);
        return "users";
    }
}
