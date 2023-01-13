package artem.strelcov.corporativeapplication.controller;

import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import artem.strelcov.corporativeapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping(value = "")
    public String showSignedUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User user1 = userService.saveUser(user);
        userRepository.findByEmail(user.getEmail());
        return "register_success";
    }
}