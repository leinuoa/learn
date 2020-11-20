package com.leinuoa.security.config;

import com.leinuoa.security.filter.JwtRequestFilter;
import com.leinuoa.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
 *  1. 将CSRF禁用掉（默认是开启的），但是这样你的网站Spring security也就失去了跨站防护的CSRF的意义;
 *  2. 添加_csrf的token，既然他需要，那我们就给他所要的。
 *      （1）使用Thymeleaf模板提交表单（th:action）的话表单里自动添加CSRF令牌，你可以在网页中查看表单中是不是多了类似的一行。
 *      （2）非此模板引擎的话。需要手动在表单当中添加。
 *
 * @author hooray
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtUserDetailsService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager () throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
        http
            .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/","/authenticate",
                        "/test/**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/*.ico")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // 忽略URL
//        web.
//            ignoring()
//            .antMatchers("/authenticate");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
