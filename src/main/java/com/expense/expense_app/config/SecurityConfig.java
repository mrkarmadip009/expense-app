package com.expense.expense_app.config;

import com.expense.expense_app.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 'EnrCoder' ko 'Encoder' karo
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Ye line fix karegi login issue: Password matching ke liye BCrypt use hoga
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Postman POST request ke liye zaroori
                .authorizeRequests()
                // Pehle specify karo kis path ko permit karna hai
                .antMatchers("/api/**").permitAll()
                // Phir baaki sab ke liye authentication mango
                .anyRequest().authenticated()
                .and()
                // Basic Auth enable karo taki Postman chal sake
                .httpBasic();
    }
}