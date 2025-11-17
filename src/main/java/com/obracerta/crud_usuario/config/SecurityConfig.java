package com.obracerta.crud_usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Adicione as importações necessárias para o CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ... (Métodos passwordEncoder e authenticationManager permanecem inalterados) ...
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    // NOVO MÉTODO: Configuração do CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Crie uma nova configuração CORS
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite todas as origens para testes. 
        // Em produção, você deve listar origens específicas (ex: "http://localhost:3000", "https://seu-dominio.com")
        configuration.setAllowedOrigins(List.of("*"));
        
        // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permite todos os cabeçalhos
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permite o envio de credenciais (cookies, headers de autenticação, etc.).
        // Se você usar List.of("*") em AllowedOrigins, isso deve ser false. 
        // Se você listar origens específicas, isso pode ser true.
        configuration.setAllowCredentials(false); 

        // Defina a configuração para todos os caminhos
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    
        http
            // NOVO: Habilita a configuração CORS (Chama o corsConfigurationSource() definido acima)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 1. Desabilita o CSRF (Correto)
            .csrf(AbstractHttpConfigurer::disable) 
            
            // 2. A LINHA MAIS IMPORTANTE AGORA
            // Força o Spring a criar uma sessão (IF_REQUIRED)
            // e NÃO usar a política STATELESS.
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            
            // 3. Suas regras de permissão (Corretas)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/usuarios/cadastro").permitAll()
                .requestMatchers("/api/usuarios/login").permitAll()
                .requestMatchers("/api/projetos").permitAll()
                .anyRequest().authenticated() 
            )
            
            // 4. Configuração do H2 (Correta)
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

}