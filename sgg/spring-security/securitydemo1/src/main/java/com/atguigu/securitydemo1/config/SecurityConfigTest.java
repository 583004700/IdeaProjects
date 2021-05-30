package com.atguigu.securitydemo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    /*
        记住我 功能需要将用户token信息存到数据库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 是否要启动的时候创建表
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 退出的路径和退出跳转到的页面
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        // 配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin()        //自定义自己编写的登录页面
                .loginPage("/login")       // 登录页面设置
                .loginProcessingUrl("/user/login")         // 登录访问路径
                .defaultSuccessUrl("/success.html").permitAll()      //登录成功之后跳转路径
                .and().authorizeRequests()
                .antMatchers("/", "/test/hello", "/user/login").permitAll()   // 设置哪些路径可以直接访问，不需要认证
                .antMatchers("/test/index")
                .hasAuthority("admins")  // 表示当前登录的用户只有具有admins权限才可以访问这个路径
                //.hasAnyAuthority("admins,manager")    // 表示当前登录的用户具有admins或者manager都可以访问
                // hasRole方法会加前缀 ROLE_    ROLE_sale
                //.hasRole("sale")            //  表示用户需要sale角色才能访问
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())     //记住我
                .tokenValiditySeconds(60)       // 记住我的有效时长，单位秒
                .userDetailsService(userDetailsService);     //记住的用户信息
                //http.csrf().disable();    // 关闭csrf防护
    }
}
