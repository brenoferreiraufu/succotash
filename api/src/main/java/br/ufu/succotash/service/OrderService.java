package br.ufu.succotash.service;

import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;
import br.ufu.succotash.mock.MockPaymentClient;
import br.ufu.succotash.mock.MockPaymentRequest;
import br.ufu.succotash.repository.OrderItemRepository;
import br.ufu.succotash.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MockPaymentClient paymentClient;

    public String newOrder(OrderRequest order) {

        var id = orderRepository.save(order.toModel().get(0).getOrder()).getId();

        for (OrderItem orderItem : order.toModel()) {
            orderItemRepository.save(orderItem);
        }

        return id;
    }

    public Order findOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    public void payOrder(String orderId) {
        var order = findOrder(orderId);
        var orderItems = orderItemRepository.findByOrder(order);

        var total = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            var price = orderItem.getItem().getPrice();
            var quantity = new BigDecimal(orderItem.getQuantity());
            total = total.add(price.multiply(quantity));
        }

        var response = paymentClient.pay(new MockPaymentRequest(total, order.getId()));

        if (response == HttpStatus.BAD_REQUEST) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}