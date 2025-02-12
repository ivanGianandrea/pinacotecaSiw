package it.uniroma3.siw.authentication;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    private static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private DataSource dataSource; //recupera le credenziali degli utenti e dei ruoli

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  //autenticazione basata su database
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
            .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?"); //recupera l utente dalla tabella credentials deve restituire username e password
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .csrf().and().cors().disable()
        .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/index","/login", "/register", "/css/**", "/images/**","/elencoOpere","/elencoOpere/cercaOpere","/elencoOpere/cercaOpere/anno","/elencoOpere/cercaOpere/tecnica","/elencoOpere/cercaOpere/artista").permitAll()
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/index")
            
            .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")
            
            .and().logout()
            .logoutUrl("/logout") //se fai una get a questo url

            .logoutSuccessUrl("/")
            .invalidateHttpSession(true) //invalida la sessione associata a cookies
            .deleteCookies("JSESSIONID") //cancella i cookies associati alla memoria
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //va alla pagina di logout
            .clearAuthentication(true).permitAll();


        return httpSecurity.build();
    }
}