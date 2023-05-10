package cinema.config;

import cinema.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions/available")
                .hasAnyAuthority(
                        Role.RoleName.ADMIN.name(),
                        Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions").hasAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/movie-sessions").hasAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/movie-sessions").hasAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET,
                        "/orders",
                        "/shopping-carts/by-user").hasAuthority(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST, "/orders/complete").hasAuthority(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasAuthority(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.GET, "/users/by-email").hasAuthority(Role.RoleName.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
