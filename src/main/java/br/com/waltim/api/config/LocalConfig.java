package br.com.waltim.api.config;

import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
