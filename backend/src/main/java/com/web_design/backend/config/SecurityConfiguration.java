package com.web_design.backend.config;

import com.alibaba.fastjson.JSONObject;
import com.web_design.backend.entity.RestBean;
import com.web_design.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    AuthorizeService authorizeService;

    @Resource
    DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           PersistentTokenRepository repository) throws Exception {
//        // 创建 AuthenticationFilter 实例
//        UsernamePasswordAuthenticationFilter authenticationFilter =
//                new JsonAuthenticationFilter();
//        // 配置 AuthenticationManager
//        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
//        // 替换过滤器
//        http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationManagerBean(), UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .usernameParameter("email")
//                .successHandler(this::onAuthenticationSuccess)
//                .failureHandler(this::onAuthenticationFailure)
//                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(this::onAuthenticationSuccess)
//                // 记住我
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(3600 * 24 * 7)

                .and()
                .csrf()
                .disable()
                // 处理跨域请求
                .cors()
                .configurationSource(this.configurationSource())
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(this::onAuthenticationFailure)
                .and()
                .build();
    }

    @Bean
    public JsonAuthenticationFilter authenticationManagerBean() {
        JsonAuthenticationFilter authenticationFilter = new JsonAuthenticationFilter();
        authenticationFilter.setAuthenticationFailureHandler(this::onAuthenticationFailure); //设置登录失败处理类
        authenticationFilter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess); //设置登录成功处理类
        authenticationFilter.setFilterProcessesUrl("/login");
//        authenticationFilter.setRememberMeServices(rememberMeServices()); //设置记住我
        authenticationFilter.setUsernameParameter("email");
        authenticationFilter.setPasswordParameter("password");
        return authenticationFilter;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 第一次启动时创建表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        // 后续启动时不创建表
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }


    // 处理跨域请求
    private CorsConfigurationSource configurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        // 调试时使用*，部署时使用具体的域名
        cors.addAllowedOriginPattern("*");

        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(authorizeService)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        if (request.getRequestURI().endsWith("/login"))
            response.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
        else if (request.getRequestURI().endsWith("/logout"))
            response.getWriter().write(JSONObject.toJSONString(RestBean.success("退出登录成功")));
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.failure(401, exception.getMessage())));
    }
}
