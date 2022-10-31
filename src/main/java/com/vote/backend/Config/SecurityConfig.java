package com.vote.backend.Config;

import com.vote.backend.Filter.JwtAuthenticationTokenFilter;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
//@EnableWebSecurity
/**拦截注释启动项*/
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
   @Autowired
   private AccessDeniedHandler accessDeniedHandler;
//   @Autowired
//   private AuthenticationEntryPoint authenticationEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    /**禁用csrf和session*/
                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()

                .authorizeRequests()//管理请求
//                .antMatchers("/free/login").permitAll()//.anonymous()//匿名访问-只有未登录状态下才能访问
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        /**
         * @Bean - 交由SpringBoot维护
         * 但是有部分组件是由子框架/工具独立维护
         * 就需要配置类/文件进行设置
         *
         * 但是子框架也可能去容器中拿取自定义的类
         * 如：PasswardEncoder,UserDetailService
         * */
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);

        /**异常处理器*/
//        http.exceptionHandling()
//                //认证失败处理器
//                .authenticationEntryPoint(authenticationEntryPoint)
//                //授权失败处理器
//                .accessDeniedHandler(accessDeniedHandler);


    }


    /**自定义加密机制*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


   @Bean
    @Override
    //ctrl+o观察选项
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}
