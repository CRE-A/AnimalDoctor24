package com.jnb.animaldoctor24.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jnb.animaldoctor24.global.config.ApplicationConfig;
import com.jnb.animaldoctor24.global.config.ApplicationConfig;
import com.jnb.animaldoctor24.global.config.security.jwt.JwtAuthenticationFilter;
import com.jnb.animaldoctor24.global.config.security.jwt.JwtAuthorizationFilter;
import com.jnb.animaldoctor24.global.config.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final CustomFailureHandler customFailureHandler;
    private final ApplicationConfig applicationConfig;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**");
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //        test1
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
//        corsConfig.setAllowedOrigins(List.of("http://localhost:5173", "null"));   // 뷰
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000", "null"));     // 리엑트
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        AuthenticationConfiguration authConfig = http.getSharedObject(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(authConfig);

        http
                .cors(config -> config.configurationSource(source))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(config -> config.disable())
                .formLogin(config -> config.disable())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JwtAuthorizationFilter(authenticationManager, objectMapper, jwtService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(config -> config
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/auth/login"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/auth/register"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/auth/social/callback"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/auth/token"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/members"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/members/email"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/members/email"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/members/password"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/error"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/hospital/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/hospital/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/review/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PATCH, "/api/v1/review/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/review/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/like/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/like/**"))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/api/vi/auth//**").permitAll()
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/api/vi/hospital/**").hasRole("ADMIN")
//                        .requestMatchers("/api/vi/**").permitAll()
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN","MANAGER")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin.loginPage("/api/vi/auth/login"))
//                .formLogin(formLogin -> formLogin.loginProcessingUrl("/api/vi/auth/authenticate"))
//                .formLogin(formLogin -> formLogin.failureHandler(customFailureHandler))
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("_rtkn")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                );
        return http.build();
    }

}
