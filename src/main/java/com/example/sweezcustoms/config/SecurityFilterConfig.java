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

            //Swagger UI
            auth.requestMatchers("/swagger-ui/**").permitAll();
            auth.requestMatchers("/v3/api-docs/**").permitAll();
            auth.requestMatchers("/swagger-ui.html").permitAll();
            auth.requestMatchers("/swagger-resources/**").permitAll();
            auth.requestMatchers("/webjars/**").permitAll();



            //Authentication
            auth.requestMatchers("/api/auth/register").permitAll();
            auth.requestMatchers("/api/auth/login").permitAll();
            auth.requestMatchers("/api/auth/reset-password").permitAll();
            auth.requestMatchers("/api/auth/password-recovery").permitAll();

            //Company Controller
            auth.requestMatchers("/api/company/create-company").authenticated();
            auth.requestMatchers("/api/company/get-by-id/*").authenticated();
            auth.requestMatchers("/api/company/get-by-company-name").authenticated();
            auth.requestMatchers("/api/company/get-employees-company/*").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");
            auth.requestMatchers("/api/company/get-by-tin").authenticated();
            auth.requestMatchers("/api/company/get-by-okpo").authenticated();
            auth.requestMatchers("/api/company/get-by-customs-code").authenticated();



            //CompanyDocument Controller
            auth.requestMatchers("/api/company-document/get-document-registration").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");

            //TnvedCode Controller
            auth.requestMatchers("/api/tnved/get-all").authenticated();
            auth.requestMatchers("/api/tnved/get-by-id/*").authenticated();
            auth.requestMatchers("/api/tnved/get-by-code").authenticated();
            auth.requestMatchers("/api/tnved/create").hasAuthority("ADMIN");
            auth.requestMatchers("/api/tnved/update/*").hasAuthority("ADMIN");
            auth.requestMatchers("/api/tnved/delete/*").hasAuthority("ADMIN");

            //DeclarationProduct Controller
            auth.requestMatchers("/api/declaration-products/add/*").authenticated();
            auth.requestMatchers("/api/declaration-products/get-by-id/*").authenticated();
            auth.requestMatchers("/api/declaration-products/get-all/*").authenticated();
            auth.requestMatchers("/api/declaration-products/update/*").authenticated();
            auth.requestMatchers("/api/declaration-products/delete/*").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");

            //Declaration status
            auth.requestMatchers("/api/company-declarations/submit/*").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");
            auth.requestMatchers("/api/company-declarations/approve/*").hasAnyAuthority("INSPECTOR", "ADMIN");
            auth.requestMatchers("/api/company-declarations/reject/*").hasAnyAuthority("INSPECTOR", "ADMIN");
            auth.requestMatchers("/api/user-declarations/submit/*").authenticated();
            auth.requestMatchers("/api/user-declarations/approve/*").hasAnyAuthority("INSPECTOR", "ADMIN");
            auth.requestMatchers("/api/user-declarations/reject/*").hasAnyAuthority("INSPECTOR", "ADMIN");

            //Company verification
            auth.requestMatchers("/api/company/pending").hasAnyAuthority("INSPECTOR", "ADMIN");
            auth.requestMatchers("/api/company/verify/*").hasAnyAuthority("INSPECTOR", "ADMIN");

            //Company employees
            auth.requestMatchers("/api/company/*/employees/*").hasAnyAuthority("OWNER", "ADMIN");

            //Branches
            auth.requestMatchers("/api/branches/**").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");

            //Car declarations
            auth.requestMatchers("/api/car-declarations/user").authenticated();
            auth.requestMatchers("/api/car-declarations/company/*").hasAnyAuthority("OWNER", "MANAGER", "ADMIN");
            auth.requestMatchers("/api/car-declarations/get-by-id/*").authenticated();
            auth.requestMatchers("/api/car-declarations/get-by-vin").authenticated();
            auth.requestMatchers("/api/car-declarations/my").authenticated();
            auth.requestMatchers("/api/car-declarations/submit/*").authenticated();
            auth.requestMatchers("/api/car-declarations/approve/*").hasAnyAuthority("INSPECTOR", "ADMIN");
            auth.requestMatchers("/api/car-declarations/reject/*").hasAnyAuthority("INSPECTOR", "ADMIN");


            auth.anyRequest().authenticated();
        });
        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        return httpSecurity.build();
    }
}
