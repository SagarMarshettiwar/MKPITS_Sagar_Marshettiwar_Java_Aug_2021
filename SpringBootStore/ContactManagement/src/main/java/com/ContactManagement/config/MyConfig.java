/*
 * package com.ContactManagement.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfiguration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import org.springframework.security.core.userdetails.UserDetailsService;
 * import org.springframework.security.web.FilterChainProxy;
 * 
 * import jakarta.servlet.Filter;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class MyConfig extends WebSecurityConfiguration {
 * 
 * @Bean public UserDetailsService getDetailsService() { return new
 * UserDetailServiceImpl(); }
 * 
 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean public DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider daoAuthenticationProvider = new
 * DaoAuthenticationProvider();
 * daoAuthenticationProvider.setUserDetailsService(this.getDetailsService());
 * daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder()); return
 * daoAuthenticationProvider; }
 * 
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
 * throws Exception { auth.authenticationProvider(authenticationProvider()); }
 * 
 * @Override public FilterChainProxy springSecurityFilterChain() throws
 * Exception { HttpSecurity http = getHttp(); http.authorizeRequests()
 * .antMatchers("/admin/**").hasRole("ADMIN")
 * .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/login")
 * .permitAll() .and() .logout() .permitAll(); return
 * super.springSecurityFilterChain(); } }
 * 
 */