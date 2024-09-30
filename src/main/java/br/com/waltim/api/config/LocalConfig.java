package br.com.waltim.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig implements CommandLineRunner {

    @Override
    public void run(String... args) {}

}
