package com.xander.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.xander.demo.service.UserDetailsServiceImpl;

@Configuration

/*
 * We want our users and thier password (hashed) will be stored in MongoDB,
 * and when a user tries to log in, the systme should check the provided
 * credentials against what is stored in the database.
 */
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;

    public SpringSecurity(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(request -> request
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/demo/**", "/user/**").authenticated()

                /*
                 * .hasRole("ADMIN") is used to check if the user has the role ADMIN.
                 * 
                 * It is checking our user role in the database. which is passed by 
                 * UserDetailsServiceImpl class where we are fetching the user
                 * and from the user we are fetching the role -
                 * 
                 * .roles(user.getRoles().toArray(new String[0]))
                 * 
                 * This is managed by AuthenticationManagerBuilder class.
                 */

                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                /*
                 * .formLogin(form -> form
                 *      .loginPage("/custom-login")
                 *      .defaultSuccessUrl("/dashboard")
                 *      .failureUrl("/custom-login?error")
                 * )
                 */
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /*
     * BCryptPasswordEncoder is a password encoder that uses the BCrypt strong
     * hashing function.
     * 
     * Use to encode the password before storing it in the database and
     * at when a user tries to log in, the system should check the provided
     * credentials
     * against what is stored in the database.
     * 
     * At the time of login the password will be encoded and compared with the
     * encoded
     * password stored in the database. If match found then user will be allowed to
     * login.
     * 
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
