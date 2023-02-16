package artem.strelcov.corporativeapplication.controller;


import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import artem.strelcov.corporativeapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/api/v1")
public class AppController {
    UserService userService;
    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String homePage(){

        return "homePage";
    }
    @GetMapping("/chat")
    public String getChat(){
        return "chat";
    }
    @GetMapping("/learning")
    public String getLearningPage(){
        return "learning_materials";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('users:read')")
    public String showUsers(Model model){
        List<User> users = userService.users();
        model.addAttribute("listUsers", users);
        return "users";
    }

}

