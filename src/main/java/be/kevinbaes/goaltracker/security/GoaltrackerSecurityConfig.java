package be.kevinbaes.goaltracker.security;

import be.kevinbaes.goaltracker.entities.GoaltrackerUser;
import be.kevinbaes.goaltracker.repositories.GoaltrackerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Configuration
@Component
public class GoaltrackerSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(GoaltrackerSecurityConfig.class);
    private GoaltrackerUserRepository userRepository;

    @Autowired
    public void setUserRepository(GoaltrackerUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("configuring goaltrackersecurityconfig");

        http
                .authorizeRequests()
                .antMatchers("/register", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
                .and().headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(defaultPasswordEncoder());
    }

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        userRepository.save(new GoaltrackerUser("user", defaultPasswordEncoder().encode("user"), new ArrayList<>()));
        return new GoaltrackerUserDetailsService();
    }
}
