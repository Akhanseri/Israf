package kz.ali.Israf.config;

import kz.ali.Israf.Service.PeopleService;
import kz.ali.Israf.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.Encoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PeopleService peopleService;

    private final MyAuthenticationSuccessHandler successHandler;

    @Autowired
     public SecurityConfig(PeopleService peopleService, MyAuthenticationSuccessHandler successHandler) {
        this.peopleService = peopleService;
        this.successHandler = successHandler;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(peopleService);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/api/**", "/error").permitAll()
                .requestMatchers("/admin/**").hasAuthority("admin")
                .requestMatchers("/user/{id}").access("@mySecurity.checkRestaurant(authentication,#id)")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .successHandler(successHandler)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        return http.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
