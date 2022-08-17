package br.ufu.succotash.repository;

import br.ufu.succotash.domain.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, String> {

    List<Table> findByRestaurantName(String restaurantName);

}
