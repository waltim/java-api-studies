package br.com.waltim.api;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ApiApplication {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey; // Remover o static aqui

    private PasswordEncoder passwordEncoder; // Mover a declaração para a classe

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Cria um codificador PBKDF2
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder =
                new Pbkdf2PasswordEncoder(secretKey, 16, 10000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        // Cria um mapa para encoders
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2PasswordEncoder);

        // Cria o DelegatingPasswordEncoder com "pbkdf2" como o método de codificação padrão
        passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        // Exemplo de uso do passwordEncoder para codificar uma senha
        String result = passwordEncoder.encode("admin234");
        System.out.println("My hash " + result);
    }
}
