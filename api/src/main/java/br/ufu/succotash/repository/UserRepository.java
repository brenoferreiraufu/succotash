package br.ufu.succotash.repository;

import br.ufu.succotash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
