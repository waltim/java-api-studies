package br.com.waltim.api.repositories;

import br.com.waltim.api.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
