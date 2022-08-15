package br.ufu.succotash.repository;

import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface OrderItemRepository  extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrder(Order order);
}
