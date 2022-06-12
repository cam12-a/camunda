package ru.maralays.mfa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.maralays.mfa.filter.CostumeAuthenticationFilter;


/**
 * @author Camara Alseny
 * @version 1.0
 * @since 28/04/2022
 *
 */
/*
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

 */
public class SecurityConfig  {
/*
//extends WebSecurityConfigurerAdapter
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
/*
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.formLogin();
       // http.addFilter(new CostumeAuthenticationFilter(authenticationManagerBean()));

    }

    /*
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    */
}
