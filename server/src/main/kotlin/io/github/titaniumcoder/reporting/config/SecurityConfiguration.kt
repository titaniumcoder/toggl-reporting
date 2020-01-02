package io.github.titaniumcoder.reporting.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@EnableWebSecurity
@Configuration
@EnableResourceServer
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/", "/index.html", "/*.json", "/favicon.ico", "/*.js", "/static/**", "/robots.txt", "/error")
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .oauth2Login().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
