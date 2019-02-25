package be.kevinbaes.goaltracker.security;

import be.kevinbaes.goaltracker.repositories.GoaltrackerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class GoaltrackerUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(GoaltrackerUserDetailsService.class);
    private PasswordEncoder passwordEncoder;
    private GoaltrackerUserRepository userRepository;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(GoaltrackerUserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.debug("My userdetailservice is being used");

        UserDetails userDetails = this.userRepository.findByUsername(userName);

        if(userDetails == null) {
            throw new UsernameNotFoundException("User " + userName + " not found.");
        }

        return userDetails;
    }

    @PostConstruct
    public void postInit() {
        if(this.passwordEncoder == null) {
            logger.error("passwordEncoder not set on MyUserDetailsService");
            throw new BeanInstantiationException(GoaltrackerUserDetailsService.class, "passwordEncoder not set on MyUserDetailsService");
        }

        if(this.userRepository == null) {
            logger.error("userRepository not set on MyUserDetailsService");
            throw new BeanInstantiationException(GoaltrackerUserDetailsService.class, "userRepository not set on MyUserDetailsService");
        }
    }
}
