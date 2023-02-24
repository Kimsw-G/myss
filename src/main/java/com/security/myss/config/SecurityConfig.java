package com.security.myss.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.security.myss.config.handler.MyLoginSuccessHandler;
import com.security.myss.service.PrincipalOauth2UserService;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    private static final String[] AUTH_WHITELIST = {
        "/", "/login", "/logout", "/signup", "/home","/oauth2/authorization/google"
    };
    private static final String[] AUTH_USER_LIST = {
        "/user"
    };
    private static final String[] AUTH_MANAGER_LIST = {
        "/manager"
    };
    private static final String[] AUTH_ADMIN_LIST = {
        "/admin"
    };

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthorityAuthorizationManager<RequestAuthorizationContext> userAuth
            = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("USER");
        AuthorityAuthorizationManager<RequestAuthorizationContext> managerAuth
            = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("MANAGER");
        AuthorityAuthorizationManager<RequestAuthorizationContext> adminAuth
            = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("ADMIN");
            
        userAuth.setRoleHierarchy(roleHierarchy());
        managerAuth.setRoleHierarchy(roleHierarchy());            
        adminAuth.setRoleHierarchy(roleHierarchy());
        http.csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                // .anyRequest().permitAll()
                // .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(AUTH_USER_LIST).access(userAuth)
                .requestMatchers(AUTH_MANAGER_LIST).access(managerAuth)
                .requestMatchers(AUTH_ADMIN_LIST).access(adminAuth)
                .anyRequest().permitAll()
            )
            // .formLogin();
            .formLogin(form->form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                // .usernameParameter("userId")
                // .passwordParameter("passwd")
                // .loginProcessingUrl("/login_proc")
                // .successHandler(new MyLoginSuccessHandler())
                // .failureHandler(null)
            )
            .oauth2Login(form->form
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
            );
            // .logout(logout->logout
            //     .logoutUrl("/logout")
            //     .logoutSuccessUrl("/login")
            //     .deleteCookies("JSESSIONID","remember-me")
            //     // .addLogoutHandler(null)
            //     // .logoutSuccessHandler(null)
            // );
            

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        String role = "ROLE_ADMIN > ROLE_MANAGER > ROLE_USER";
        RoleHierarchyImpl r = new RoleHierarchyImpl();
        r.setHierarchy(role);
        return r;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails banana = User.builder()
            .username("banana")
            .password(passwordEncoder().encode("banana"))
            .roles("USER")
            .build();
        UserDetails furu = User.builder()
            .username("furu")
            .password(passwordEncoder().encode("furu"))
            .roles("MANAGER")
            .build();
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        List<UserDetails> list = new ArrayList<>();
        list.add(banana);
        list.add(furu);
        list.add(admin);

        return new InMemoryUserDetailsManager(list);
    }
}
