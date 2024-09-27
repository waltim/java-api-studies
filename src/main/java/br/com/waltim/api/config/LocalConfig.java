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

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) {
        startDB();
    }

    private void startDB() {

        Address address = new Address("Main Street", "123", "Apt 4B", "Springfield", "IL", "USA");

        Users u1 = new Users(null, "Carol", "carolzinha@teste.com",address, "123");
        Users u2 = new Users(null, "Elisa", "xuxu@teste.com",address, "123");

        repository.saveAll(List.of(u1, u2));
    }
}
