package BananaBrain.security;

import BananaBrain.model.MyAppUser;
import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.security.JwtAuthenticationFilter;
import BananaBrain.service.MyAppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.SessionCreationPolicy;

import java.io.IOException;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {

    @Autowired
    private MyAppUserService appUserService;

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(httpForm -> {
                    httpForm.loginPage("/login").permitAll();
                    httpForm.defaultSuccessUrl("/index");
//                    httpForm.successHandler(new AuthenticationSuccessHandler() {
//                        @Override
//                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//                            MyAppUser user = (MyAppUser) appUserService.loadUserByUsername(authentication.getName());
//                            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("student"))) {
//                                response.sendRedirect("/student/home");
//                            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("teacher"))) {
//                                response.sendRedirect("/teacher/home");
//                            } else {
//                                response.sendRedirect("/index");
//                            }
//                        }
//                    });
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/api/auth/**", "/req/signup", "/role", "/css/**", "/js/**", "/assets/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }
}
