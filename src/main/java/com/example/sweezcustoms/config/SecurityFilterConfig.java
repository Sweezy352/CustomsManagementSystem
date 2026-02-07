package com.example.sweezcustoms.config;

import com.example.sweezcustoms.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> {
            cors.configurationSource(corsConfigurer -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOriginPatterns(List.of("*", "http://localhost:3000"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                return configuration;
            });
        });

        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/auth/register").permitAll();
            auth.requestMatchers("/api/auth/login").permitAll();
            auth.requestMatchers("/error").permitAll();
            auth.requestMatchers("/api/auth/reset-password").permitAll();
            auth.requestMatchers("/api/auth/password-recovery").permitAll();

            //Company Controller
            auth.requestMatchers("/api/company/create-company").authenticated();
            auth.requestMatchers("/api/company/get-by-id/*").authenticated();
            auth.requestMatchers("/api/company/get-by-company-name").authenticated();
            auth.requestMatchers("/api/company/get-employees-company/*").hasAnyAuthority("OWNER", "MANAGER");
            auth.requestMatchers("/api/company/get-by-tin").authenticated();
            auth.requestMatchers("/api/company/get-by-okpo").authenticated();
            auth.requestMatchers("/api/company/get-by-customs-code").authenticated();


            //CompanyDocument Controller
            auth.requestMatchers("/api/company-document/get-document-registration").hasAnyAuthority("OWNER", "MANAGER");


            auth.anyRequest().authenticated();
        });
        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        return httpSecurity.build();
    }
}
