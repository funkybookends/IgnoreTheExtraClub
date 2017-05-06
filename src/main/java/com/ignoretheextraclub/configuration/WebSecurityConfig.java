package com.ignoretheextraclub.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by caspar on 05/03/17.
 */
@EnableWebSecurity
@Configuration
@Import(PasswordEncoderConfiguration.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private @Autowired UserDetailsService userDetailsService;
    private @Autowired PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()

                .antMatchers("/",
                        "/home",
//                        DefaultErrorController.ERROR_PATH,
                        "/p/**",
                        "/register",
                        "/css/**",
                        "/img/**",
                        "/js/**"
                        )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .rememberMe()
                .useSecureCookie(true);
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
