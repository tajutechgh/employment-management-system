package com.tajutechgh.ems.configuration;

import com.tajutechgh.ems.security.jwt.JwtAuthenticationEntrypoint;
import com.tajutechgh.ems.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfiguration {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntrypoint authenticationEntrypoint;
    private JwtAuthenticationFilter authenticationFilter;

    public SpringSecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationEntrypoint authenticationEntrypoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntrypoint = authenticationEntrypoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> {

            authorize.requestMatchers(HttpMethod.GET, "/api/employees/all").hasAnyAuthority("Admin", "User");
            authorize.requestMatchers(HttpMethod.GET, "/api/employees/get/{id}").hasAnyAuthority("Admin", "User");
            authorize.requestMatchers(HttpMethod.POST, "/api/employees/create").hasAuthority("Admin");
            authorize.requestMatchers(HttpMethod.PUT, "/api/employees/update/{id}").hasAuthority("Admin");
            authorize.requestMatchers(HttpMethod.DELETE, "/api/employees/delete/{id}").hasAuthority("Admin");
            authorize.requestMatchers(HttpMethod.GET, "/api/departments/all").hasAnyAuthority("Admin", "User");
            authorize.requestMatchers(HttpMethod.GET, "/api/departments/get/{id}").hasAnyAuthority("Admin", "User");
            authorize.requestMatchers(HttpMethod.POST, "/api/departments/create").hasAuthority("Admin");
            authorize.requestMatchers(HttpMethod.PUT, "/api/departments/update/{id}").hasAuthority("Admin");
            authorize.requestMatchers(HttpMethod.DELETE, "/api/departments/delete/{id}").hasAuthority("Admin");
            authorize.requestMatchers("/api/auth/**").permitAll();
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            authorize.anyRequest().authenticated();

        }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling((exception) -> exception.authenticationEntryPoint( authenticationEntrypoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
