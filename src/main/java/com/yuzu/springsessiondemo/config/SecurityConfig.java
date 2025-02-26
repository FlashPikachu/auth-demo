package com.yuzu.springsessiondemo.config;

import com.yuzu.springsessiondemo.filter.LoginFilter;
import com.yuzu.springsessiondemo.handler.*;
import com.yuzu.springsessiondemo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJdbcHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    final CustomUserDetailsService userService;
    final PasswordEncoder passwordEncoder;
    final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
//    final TokenFilter tokenFilter;
    final ApiAccessDeniedHandler apiAccessDeniedHandler;

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/login");
        loginFilter.setAuthenticationManager(authenticationManager());
        loginFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
//                        "/test/**",
                        "/assets/**",
                        "/",
                        "/**/*.js",
                        "/lang/*.json",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.map",
                        "/**/*.html",
                        "/**/*.png",
                        "/**/*.svg"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeRequests()
                .antMatchers(
                        "/test/**",
                        "/login",
                        "/fms/login",
//                        "/fms/error",
                        "/fms-ui/**",
                        "/auth/api/config",
                        "/v2/api-docs",
//                        "/fis-proxy/heartbeat",
//                        "/fis-proxy/register",
//                        "/fisCluster/fisClusterInfo",
//                        "/user/lockExpirationTime",
                        "/swagger-resources/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/swagger-ui.html",
                        "/doc.html",
                        "/webjars/**",
                        "/doc.html",
                        "/provisioned/mtas",
                        "/bulk-task/api/v1/**",
                        "/fms-ui/**",
                        "/auth/api/config",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/swagger-ui.html",
                        "/doc.html",
                        "/webjars/**",
                        "/doc.html",
                        "/provisioned/mtas",
                        "/bulk-task/api/v1/**",
                        "/fms/ProcessTop/**",
                        "/ProcessTop/**")
                .permitAll()
//                .anyRequest()
//                .authenticated()
                .and()
                .logout(logout -> {
                    logout.invalidateHttpSession(true)
                            .logoutSuccessHandler(new MyAuthenticationLogoutHandler())
                            .deleteCookies("SESSION");
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(apiAccessDeniedHandler)
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .anonymous()
                .disable()
                .csrf().disable();

    }
}

