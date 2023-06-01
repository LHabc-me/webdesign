package com.web_design.backend.config;

import com.web_design.backend.config.authentication.MyAuthenticationFilter;
import com.web_design.backend.config.authentication.MyAuthenticationHandler;
import com.web_design.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    AuthorizeService authorizeService;

    @Resource
    DataSource dataSource;

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           MyAuthenticationFilter authenticationFilter,
                                           MyAuthenticationHandler authenticationHandler,
                                           PersistentTokenRepository repository) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 禁用表单登录
                .formLogin()
                .disable()
//                .loginProcessingUrl("/login")
//                .usernameParameter("email")
//                .successHandler(this::onAuthenticationSuccess)
//                .failureHandler(this::onAuthenticationFailure)

                // 记住我
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(3600 * 24 * 7)

                // 登出
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(authenticationHandler);

        http.csrf()
                .disable()
                // 处理跨域请求
                .cors()
                .configurationSource(this.configurationSource())
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationHandler);

        return http.build();
    }

    @Bean
    public MyAuthenticationFilter authenticationManagerBean(AuthenticationManager authenticationManager,
                                                            MyAuthenticationHandler authenticationHandler) throws Exception {
        MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(authenticationManager, authenticationHandler);
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
}
