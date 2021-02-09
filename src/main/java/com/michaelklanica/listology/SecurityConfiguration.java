package com.michaelklanica.listology;

import com.michaelklanica.listology.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersLoader).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                /* Pages that can be viewed without having to log in */
                .antMatchers(
                        "/",
                        "/post/all",
                        "/user/register"
                ).permitAll()
                /* Pages that require authentication */
                .antMatchers(
                        "/post/{id}/*",
                        "/post/{id}",
                        "/user/{id}",
                        "/dashboard",
                        "/user/{id}/*"
                ).authenticated()
                /* Pages that require a role */
//                .antMatchers("/admin/*","/admin").hasRole("Admin")
                .and()
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard").permitAll()  //  all can access login page & on success redirect, it can be any URL
                /* Logout configuration */
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout") // append a query string value
                .and()
                /* remember me feature */
                .rememberMe()
                .tokenValiditySeconds(2592000) // sets expiration of token for remember me
        ;
    }
}
