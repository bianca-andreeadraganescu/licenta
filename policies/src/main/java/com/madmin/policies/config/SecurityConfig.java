package com.madmin.policies.config;

import com.madmin.policies.utils.JwtTokenFilter;
import com.madmin.policies.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/api/auth/main").permitAll()// Allow access to the login endpoint
//                    .antMatchers("/api/auth/login").permitAll()
//                    .antMatchers("/login.html").permitAll()
//                    .antMatchers("/api/policies/dev").hasAuthority("ADMIN")
//                    .antMatchers("/api/policies/default").hasAuthority("ADMIN")
//                    .antMatchers("/api/policies/lab").hasAuthority("ADMIN")
//                    .antMatchers("/api/policies/exam").hasAuthority("ADMIN")
//                    .anyRequest().authenticated()
//                .and()
//                    .addFilterBefore(jwtTokenFilterBean(), UsernamePasswordAuthenticationFilter.class) // Use jwtTokenFilterBean() here
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    }

//    @Bean
//    public JwtTokenFilter jwtTokenFilterBean() throws Exception {
//        return new JwtTokenFilter(authService, new JwtTokenProvider(userDetailsService()), authenticationManagerBean());
//    }

//    @Bean
//    public JwtTokenProvider jwtTokenProvider() {
//        return new JwtTokenProvider(userDetailsService());
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
