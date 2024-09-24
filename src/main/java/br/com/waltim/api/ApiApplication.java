package br.com.waltim.api;

import br.com.waltim.api.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

        User user = new User(1L, "Walter", "waltim@foton.la", "1234");

        System.out.println(user.toString());
    }

}
