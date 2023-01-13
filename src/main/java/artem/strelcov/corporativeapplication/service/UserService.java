package artem.strelcov.corporativeapplication.service;

import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service

public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void register(User user, String siteURL)
                throws UnsupportedEncodingException, MessagingException{
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);
            userRepository.save(user);
            sendVerificationEmail(user, siteURL);
    }
    private void sendVerificationEmail(User user, String siteURL)
                throws UnsupportedEncodingException, MessagingException{
            String toAddress = user.getEmail();
            String fromAddress = "sophisticatedthings@mail.ru";
            String senderName = "Corporative";
            String subject = "Please, verify your registration";
            String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Corporative";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(fromAddress, senderName);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        messageHelper.setText(content, true);
        javaMailSender.send(message);













    }
}
