package br.ufu.succotash.repository;

import br.ufu.succotash.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {}
