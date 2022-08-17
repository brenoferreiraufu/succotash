package br.ufu.succotash.repository;

import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus status, String tableId);
}
