package com.example.make91.Bookstore;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.make91.Bookstore.web.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserDetailServiceImpl userDetailsService;

  	@Autowired
	DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests().antMatchers("/signup", "/saveuser").permitAll()
          .and()
		.authorizeRequests()
          .anyRequest().authenticated()
          .and()
		.formLogin()
		  .loginPage("/login")
		  .defaultSuccessUrl("/booklist")
		  .permitAll()
		  .and()
        .rememberMe()
          .rememberMeCookieName("bookstore-remember-me")
          .tokenValiditySeconds(365 * 24 * 60 * 60) // expired time = 1 year
          .tokenRepository(persistentTokenRepository())
          .and()
		.csrf().disable()
		.logout()
		  .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

  	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //tokenRepository.setCreateTableOnStartup(true);  //this should only be used once to create the sql table
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}