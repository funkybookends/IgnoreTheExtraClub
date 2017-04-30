package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.controllers.ErrorController;
import com.ignoretheextraclub.model.user.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by caspar on 05/03/17.
 */
@EnableWebSecurity
@Configuration
@Import(PasswordEncoderConfiguration.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    public static final String IS_ADMIN = "hasRole('ADMIN')";
    public static final String ADMIN = "admin";

    private
    @Autowired
    UserDetailsService userDetailsService;
    private
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override

    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers(
                    "/",
                    "/home",
                    ErrorController.ERROR_PATH,
                    "/p/**",
                    "/register"
                ).permitAll()
            .antMatchers("/" + ADMIN + "/**")
                .hasAuthority(Permission.ADMIN.toString())
            .anyRequest().authenticated()
                .and()
            .formLogin().loginPage("/login").permitAll()
                .and()
            .logout().permitAll()
                .and()
            .rememberMe().useSecureCookie(true);
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }
}
