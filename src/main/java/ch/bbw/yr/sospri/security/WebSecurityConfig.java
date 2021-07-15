package ch.bbw.yr.sospri.security;
/**
 * @Author: Yannick Ruck
 * @Date: 28/05/2021
 */
import ch.bbw.yr.sospri.controller.RegisterController;
import ch.bbw.yr.sospri.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(RegisterController.GetPep(), 185000, 256);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

        http.authorizeRequests()
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/css/*", "/fragments/*", "/img/*", "/errors/*", "/get-register").permitAll()
                .antMatchers("/get-channel?chatroom=webframeworks").hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/get-channel?chatroom=technotrends").hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/get-channel?chatroom=fun").hasAnyAuthority("admin", "supervisor", "member")
                .regexMatchers("\\/get-channel\\?chatroom\\=topsecret").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().oauth2Login().loginPage("/login").permitAll().defaultSuccessUrl("/loginSuccess")
                .and().logout().permitAll()
                .and().httpBasic()
                .and().exceptionHandling().accessDeniedPage("/403.html")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).invalidSessionUrl("/login");

        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
    }

}
