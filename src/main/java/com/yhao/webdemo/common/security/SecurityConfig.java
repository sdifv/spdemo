package com.yhao.webdemo.common.security;

import com.yhao.webdemo.common.security.handler.JwtAccessDeniedHandler;
import com.yhao.webdemo.common.security.handler.JwtAuthenticationEntryPoint;
import com.yhao.webdemo.common.security.handler.LoginFailureHandler;
import com.yhao.webdemo.common.security.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CaptchaFilter captchaFilter;

    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    LocalUserDetailsService userDetailsService;

    private static final String[] WHIHTLIST = {"/login", "/logout", "/captcha", "/favicon.ico"};

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(LocalUserDetailsService userDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setUserDetailsPasswordService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin(login -> login
                .successHandler(loginSuccessHandler)
                .defaultSuccessUrl("/private", false)
                .failureHandler(loginFailureHandler));
        http.logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler));

        http.authorizeRequests(auth -> auth
                .antMatchers("/captcha", "/favicon.ico").permitAll()
                .antMatchers("/login", "/logout", "/publicPage").permitAll()
                .anyRequest().authenticated());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // addFilter：添加到最后
        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, CaptchaFilter.class);

        http.exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        return http.build();

    }
}
