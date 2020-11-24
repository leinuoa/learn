package com.leinuoa.security.config;

import com.leinuoa.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的配置类 WebSecurityConfig
 * 通过 @EnableWebMvcSecurity 注解开启Spring Security的功能
 * 继承 WebSecurityConfigurerAdapter ，并重写它的方法来设置一些web安全的细节
 * configure(HttpSecurity http) 方法
 * 通过 authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护。
 * 例如以上代码指定了 / 和 /index 不需要任何认证就可以访问，其他的路径都必须通过身份验证。
 * 通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
 *
 * 注意： springSecurity在2.0之后就会默认自动开启CSRF跨站防护，仅仅GET|HEAD|TRACE|OPTIONS这4类方法会被放行。源码：DefaultRequiresCsrfMatcher.class
 * 解决办法：
 *  1. 将CSRF禁用掉（默认是开启的），但是这样你的网站Spring security也就失去了跨站防护的CSRF的意义(使用jwt可以禁用掉CSRF);
 *  2. 添加_csrf的token，既然他需要，那我们就给他所要的。
 *      （1）使用Thymeleaf模板提交表单（th:action）的话表单里自动添加CSRF令牌，你可以在网页中查看表单中是不是多了类似的一行。
 *      （2）非此模板引擎的话。需要手动在表单当中添加。
 *
 * @author hooray
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(jwtUserDetailService())
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    protected UserDetailsService jwtUserDetailService() {
        return new JwtUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtRequestFilter requestFilter() throws Exception{
        return new JwtRequestFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager () throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 使用jwt，不需要csrf
                .csrf().disable()
                // 使用token，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                // 无需权限即可访问的资源
                .antMatchers(
                    HttpMethod.GET
                    ,"/"
                    ,"/login"
                    ,"/*.html"
                    ,"/favicon.ico"
                    ,"/js/**"
                    ,"/ts/**"
                    ,"/css/**"
                    ,"/img/**"
                )
                .permitAll()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/sys/login").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint());


        // 添加JWT filter
        http.addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        http.headers().cacheControl();
    }

}
