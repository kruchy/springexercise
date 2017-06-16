package pl.edu.agh.kruchy.repository;

import pl.edu.agh.kruchy.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
