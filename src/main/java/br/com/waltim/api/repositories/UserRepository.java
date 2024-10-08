package br.com.waltim.api.repositories;

import br.com.waltim.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_BY_USERNAME_QUERY = "SELECT u FROM User u WHERE u.userName = :username";

    Optional<User> findByEmail(String email);

    @Query(FIND_BY_USERNAME_QUERY)
    User findByUsername(@Param("username") String username);
}
