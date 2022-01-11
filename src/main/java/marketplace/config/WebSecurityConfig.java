package marketplace.config;

import marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static marketplace.constants.ApplicationConstants.OPENED_URLS;

/**
 * This is a configuration class needs to configure security tier of an application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_ERROR_URL = "/login?error=1";
    private static final String LOGOUT_URL = "/logout";
    private static final String ROOT_URL = "/";
    private static final String JSESSIONID = "JSESSIONID";

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Main configuration of spring security.
     * Configures opened and closed urls, login logic, logout logic, session and etc.
     *
     * @param http instance of class needs to configure spring security.
     * @throws Exception would be thrown if exception occur.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(OPENED_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_URL)
                .permitAll()
                .successForwardUrl(ROOT_URL)
                .failureForwardUrl(LOGIN_ERROR_URL)
                .and()
                .logout()
                .logoutUrl(LOGOUT_URL)
                .permitAll()
                .logoutSuccessUrl(ROOT_URL)
                .invalidateHttpSession(true)
                .deleteCookies(JSESSIONID);
    }

    /**
     * Adds <code>UserService</code> and <code>BCryptPasswordEncoder</code>
     *
     * @param auth is an authentication manager.
     * @throws Exception would be thrown if exception occur.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

}
