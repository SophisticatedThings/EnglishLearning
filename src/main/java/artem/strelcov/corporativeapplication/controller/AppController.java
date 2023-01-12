package artem.strelcov.corporativeapplication.controller;


import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@PreAuthorize("hasAuthority('index:read')")
public class AppController {
    UserRepository userRepository;
    @Autowired
    public AppController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/some")
    @PreAuthorize("hasAuthority('index:read')")
    public String viewHomePage() {
        return "index";
    }
    private boolean isUserValid(User user) {
        return true;
    }

}

