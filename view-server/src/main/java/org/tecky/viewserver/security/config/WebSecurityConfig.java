package org.tecky.viewserver.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.tecky.viewserver.security.config.impl.RedirectRequestCache;
import org.tecky.viewserver.security.filter.JwtRequestFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Autowired
    RedirectRequestCache redirectRequestCache;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf()
                .disable().requestCache().requestCache(redirectRequestCache).and()
                .formLogin()
                .loginPage("/user/login")
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/user/login").permitAll()

                .antMatchers("/index.html").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/**/*.js").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/**/*.css").permitAll()
                .antMatchers("/*.ico").permitAll()
                .antMatchers("/**/*.ico").permitAll()
                .antMatchers("/*.jpg").permitAll()
                .antMatchers("/**/*.jpg").permitAll()
                .anyRequest().authenticated()
                .and();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET","POST"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}