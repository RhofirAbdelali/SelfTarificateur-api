package miage.abdelali.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import miage.abdelali.app.services.impl.DbUserDetailsService;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  public AuthenticationProvider authenticationProvider(DbUserDetailsService uds, PasswordEncoder encoder) {
    DaoAuthenticationProvider p = new DaoAuthenticationProvider();
    p.setUserDetailsService(uds);
    p.setPasswordEncoder(encoder);
    return p;
  }

  @Bean
  public SecurityFilterChain filterChain(
      org.springframework.security.config.annotation.web.builders.HttpSecurity http,
      JwtAuthFilter jwtAuthFilter,
      AuthenticationProvider authProvider) throws Exception {

    http
      .csrf(csrf -> csrf.disable())
      .cors(c -> c.configurationSource(corsConfigurationSource()))
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authProvider)
      .authorizeHttpRequests(auth -> auth
        // CORS
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/subscriptions").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/tariff-versions/active").permitAll()
        // publics
        .requestMatchers(
            "/api/ping",
            "/api/auth/login",
            "/api/auth/register",
            "/error",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
        ).permitAll() 
        .requestMatchers("/api/tariffs/**").permitAll() 
        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
 
        .requestMatchers("/api/admin/**").hasRole("ADMIN")
 
        .anyRequest().authenticated()
      ) 
      .httpBasic(b -> b.disable());

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
    return cfg.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}
