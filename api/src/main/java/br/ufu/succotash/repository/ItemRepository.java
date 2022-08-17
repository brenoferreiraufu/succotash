package br.ufu.succotash.repository;

import br.ufu.succotash.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByRestaurantName(String restaurantName);
}
