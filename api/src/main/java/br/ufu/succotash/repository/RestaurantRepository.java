package br.ufu.succotash.repository;

import br.ufu.succotash.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {}
