package artem.strelcov.corporativeapplication.security;

import artem.strelcov.corporativeapplication.DAO.UserRepository;
import artem.strelcov.corporativeapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        return new CustomUserDetails(user);

    }
}
