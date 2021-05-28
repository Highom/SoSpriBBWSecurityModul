package ch.bbw.yr.sospri.security;
/**
 * @Author: Yannick Ruck
 * @Date: 28/05/2021
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }

    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Using default configure(HttpSecurity)." +
                "If subclassed this will potentially override subclass configure(HttpSecurity).");

        http.authorizeRequests()
                .antMatchers("/noSecurity").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
    }

}
