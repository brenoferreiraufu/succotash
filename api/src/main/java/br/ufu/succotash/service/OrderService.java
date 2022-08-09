package br.ufu.succotash.service;

import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.domain.model.OrderItem;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.repository.OrderItemRepository;
import br.ufu.succotash.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;



    public String newOrder(OrderRequest order) {

        orderRepository.save(order.toModel().get(0).getOrder());

        for(OrderItem orderItem : order.toModel()) {
            orderItemRepository.save(orderItem);
       }

        return "";
    }

    public Optional<User> findOrder(String orderId) {
        return null;
    }


}
