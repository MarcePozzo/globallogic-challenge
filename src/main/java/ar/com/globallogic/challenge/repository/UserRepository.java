package ar.com.globallogic.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.globallogic.challenge.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByEmail(String email);

}
