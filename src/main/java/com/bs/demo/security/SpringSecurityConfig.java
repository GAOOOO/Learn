package com.bs.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置
 * @author gf
 * @createTime 2021/12/28
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {




    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 验证码拦截器
     */
    @Autowired
    private VerifyCodeFilter verifyCodeFilter;

    /**
     * 无权限拦截器
     */
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 登录成功逻辑
     */
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    /**
     * 退出成功逻辑
     */
    @Autowired
    private LogOutSuccessHandler logOutSuccessHandler;

    /**
     * 登录失败逻辑
     */
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 无权访问 JSON 格式的数据
     */
    @Autowired
    private RestfulAccessDeniedHandler accessDeniedHandler;


    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源
        web.ignoring()
                .antMatchers(HttpMethod.GET,
                        "/swagger-resources/**",
                        "/admin/**",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v2/**",
                        "/druid/**");
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.removeConfigurers(DefaultLoginPageConfigurer.class);
        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        // 开启跨域共享，跨域伪造请求限制=无效
        http.cors().and().csrf().disable()
                //未登陆时返回 JSON 格式的数据给前端
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                //过滤请求
                .authorizeRequests()
                .antMatchers("/register", "/captcha","/druid/**","/alipay/resultNotify","/getAvatar","/notice/*","/publicCon/*","/learnRes/upload_file").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("password")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe().rememberMeParameter("rememberme")
                .and()
                .logout()
                .logoutSuccessHandler(logOutSuccessHandler)
                .deleteCookies("JSESSIONID");
        // 禁用缓存
        http.headers().cacheControl();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint);
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }



}
