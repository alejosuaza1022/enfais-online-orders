package com.enfasis.onlineorders.config;

import com.enfasis.onlineorders.security.filter.RateLimitFilter;
import com.enfasis.onlineorders.security.jwtauth.JwtAuthEntryPoint;
import com.enfasis.onlineorders.security.jwtauth.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@AllArgsConstructor
public class WebSecurity   {

    private JwtRequestFilter jwtRequestFilter;
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    private RateLimitFilter rateLimitFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/users").permitAll();
        httpSecurity.authorizeRequests().anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint )
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(rateLimitFilter, ChannelProcessingFilter.class);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
