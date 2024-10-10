package br.com.waltim.api.config;

import java.util.HashMap;
import java.util.Map;

import br.com.waltim.api.security.jwt.JwtTokenFilter;
import br.com.waltim.api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey; // Injetando a chave secreta do application-local.yml

    private final JwtTokenProvider tokenProvider;

    public SecurityConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Parâmetros de configuração
        int saltLength = 16; // Comprimento do sal em bytes
        int iterations = 10000; // Número de iterações

        // Cria uma instância de Pbkdf2PasswordEncoder usando a chave secreta do YAML
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder =
                new Pbkdf2PasswordEncoder(secretKey, saltLength, iterations, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        // Configura o delegating password encoder
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2PasswordEncoder);
        DelegatingPasswordEncoder delegatingPasswordEncoder =
                new DelegatingPasswordEncoder("pbkdf2", encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);

        return delegatingPasswordEncoder;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF (não necessário para APIs stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/signin", // Login endpoint
                                "/auth/refresh/**", // Refresh token endpoint
                                "/v3/api-docs/**", // Adaptação para o Swagger 3.x
                                "/swagger-ui/**",
                                "/swagger-ui.html" // Garante que swagger-ui.html seja permitido
                        ).permitAll() // Acesso irrestrito a estas URLs
                        .requestMatchers("/v1/**").authenticated() // Exige autenticação para rotas /v1/**
                        .requestMatchers("/v1/users").denyAll()// Bloqueia completamente o acesso à rota "/users"
                        .requestMatchers("/v1/books").denyAll()// Bloqueia completamente o acesso à rota "/book"
                )
                .cors(Customizer.withDefaults());

        // Adiciona o filtro JwtTokenFilter antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Retorna a configuração do SecurityFilterChain
    }


}
