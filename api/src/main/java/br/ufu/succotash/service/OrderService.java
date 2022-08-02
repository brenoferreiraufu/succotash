package br.ufu.succotash.service;

import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public String newOrder(OrderRequest order) {
        return orderRepository.save(order.toModel()).getId();
    }

    public Optional<User> findOrder(String orderId) {
        return null;
    }


}
